//package com.cloud.oauth2.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//
//@Configuration
//public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired(required = false)
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        // 定义哪些客户端可以注册到了服务
//        clients.inMemory()
//                .withClient("clientId")
//                .secret("{noop}clientSecret")
//                // 支持的授权模式 密码模式和客户端凭证
//                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
//                // 定义访问作用域，也就是当用户使用某一个scope授权之后，可以根据不同的scope封装不同的user信息，比如webclient会封装角色，mobileclient封装角色和资源api，由开发人员定义即可
//                .scopes("webclient", "mobileclient");
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        // 使用默认的验证管理器和用户信息服务
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService);
//    }
//}
