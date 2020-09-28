//package com.cloud.oauth2userserver.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
///**
// * SecurityConfig
// *
// * @author fengzheng
// * @date 2019/10/11
// *
// * 在 OAuth2 的概念里，所有的接口都被称为资源，接口的权限也就是资源的权限
// * 所以 Spring Security OAuth2 中提供了关于资源的注解
// * @EnableResourceServer 和 @EnableWebSecurity 的作用类似
// */
//@Configuration
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Value("${security.oauth2.client.client-id}")
//    private String clientId;
//
//    @Value("${security.oauth2.client.client-secret}")
//    private String secret;
//
//    @Value("${security.oauth2.authorization.check-token-access}")
//    private String checkTokenEndpointUrl;
//
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public TokenStore redisTokenStore (){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//    @Bean
//    public RemoteTokenServices tokenService() {
//        RemoteTokenServices tokenService = new RemoteTokenServices();
//        tokenService.setClientId(clientId);
//        tokenService.setClientSecret(secret);
//        tokenService.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
//        return tokenService;
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenServices(tokenService());
//    }
//
//    /****************************
//     * 以上是 RedisToken 方式的配置
//     * --------------------------
//     * 以下是 JWT 方式配置
//     ****************************/
//
////    @Bean
////    public TokenStore jwtTokenStore() {
////        return new JwtTokenStore(jwtAccessTokenConverter());
////    }
////
////    @Bean
////    public JwtAccessTokenConverter jwtAccessTokenConverter() {
////        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
////
////        accessTokenConverter.setSigningKey("dev");
////        accessTokenConverter.setVerifierKey("dev");
////        return accessTokenConverter;
////    }
////
////    @Autowired
////    private TokenStore jwtTokenStore;
////
////    @Override
////    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
////        resources.tokenStore(jwtTokenStore);
////    }
//}
