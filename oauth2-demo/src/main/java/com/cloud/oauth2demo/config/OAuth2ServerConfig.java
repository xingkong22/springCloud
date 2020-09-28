package com.cloud.oauth2demo.config;

import com.cloud.oauth2demo.bean.UserVoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Security OAuth2 架构上分为Authorization Server认证服务器和Resource Server资源服务器。我们可以为每一个Resource Server（一个微服务实例）设置一个resourceid。
 * Authorization Server给client第三方客户端授权的时候，可以设置这个client可以访问哪一些Resource Server资源服务，如果没设置，就是对所有的Resource Server都有访问权限
 *
 * 在Spring Security的FilterChain中，OAuth2AuthenticationProcessingFilter在FilterSecurityInterceptor的前面，所以会先验证client有没有此resource的权限，
 * 只有在有此resource的权限的情况下，才会再去做进一步的进行其他验证的判断
 *
 * 作者：字母哥课堂
 * 链接：https://www.jianshu.com/p/73ee7436fe7b
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Configuration
@Order(Integer.MIN_VALUE)
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String DEMO_RESOURCE_ID = "vcloud";

    private static final String SCOPE = "scope";

    private static final String CLIENT_ID = "client_id";

    private static final String CLIENT_SECRET = "client_secret";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;
        @Autowired
        private UserDetailsService userDetailsService;
        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        /**
         *
         *  该方法定义了那些客户端将注册到服务
         *
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            /*
            .inMemory内存验证
            withClient 和secret 提供注册的应用程序的名称，以及密钥，
                该密钥在应用程序调用OAuth2服务器以接受OAuth2访问令牌时提供。
            scopes 方法用于定义调用程序在请求OAuth2服务器获取访问令牌时可操作性的范围。
            authorizedGrantTypes 授权类型列表
        */
            //配置客户端
            clients.inMemory()
                    .withClient(CLIENT_ID)
                    .resourceIds(DEMO_RESOURCE_ID)
                    .authorizedGrantTypes("password", "refresh_token")//支持授权类型
                    .scopes(SCOPE)
                    .authorities("oauth2")
                    .secret(CLIENT_SECRET);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
            endpoints
                    .tokenEnhancer(tokenEnhancerChain)
                    .tokenStore(redisTokenStore())
                    .accessTokenConverter(accessTokenConverter())
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService)
                    // 2018-4-3 增加配置，允许 GET、POST 请求获取 token，即访问端点：oauth/token
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
            endpoints.reuseRefreshTokens(true);
            //oauth2登录异常处理
        }

        /**
         * tokenstore 定制化处理
         * @return TokenStore
         */
        @Bean
        public TokenStore redisTokenStore() {
            RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
            //redis key 前缀
            tokenStore.setPrefix(DEMO_RESOURCE_ID+"_");
            return tokenStore;
        }



        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security
                    .allowFormAuthenticationForClients()//允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401
                    .tokenKeyAccess("isAuthenticated()")//获取 token 接口
                    .checkTokenAccess("permitAll()");//允许已授权用户访问 checkToken 接口
        }

        /**
         * @Author Pan Weilong
         * @Description jwt加密秘钥
         * @Date 17:58 2019/7/10
         **/
        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(DEMO_RESOURCE_ID);
            return converter;
        }

        /**
         * jwt 生成token 定制化处理
         * @return TokenEnhancer
         */
        @Bean
        public TokenEnhancer tokenEnhancer() {
            return (accessToken, authentication) -> {
                UserVoDetail userDto = (UserVoDetail) authentication.getUserAuthentication().getPrincipal();
                final Map<String, Object> additionalInfo = new HashMap<>(1);
                additionalInfo.put("license", DEMO_RESOURCE_ID);
                additionalInfo.put("userId" , userDto.getUserId());
                additionalInfo.put("roles", userDto.getRoles());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
                //设置token的过期时间30分钟
                Calendar nowTime = Calendar.getInstance();
                nowTime.add(Calendar.MINUTE, 30);
                ((DefaultOAuth2AccessToken) accessToken).setExpiration(nowTime.getTime());
                System.out.println("accessToken:" + accessToken);
                return accessToken;
            };
        }


    }
}
