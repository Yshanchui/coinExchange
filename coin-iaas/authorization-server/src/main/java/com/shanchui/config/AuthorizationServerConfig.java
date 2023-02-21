package com.shanchui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@EnableAuthorizationServer  // 开启授权服务器
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("userServiceDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    /*
    * 添加第三方的客户端
    * */

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("coin-api") // 客户端名称
                .secret(passwordEncoder.encode("coin-secret")) // 客户端密码
                .scopes("all") // 客户端的权限范围
                .authorizedGrantTypes("password","refresh_token") // 客户端的授权模式
                .accessTokenValiditySeconds(7*24*3600) // token的有效期
                .refreshTokenValiditySeconds(30*24*3600)
                .and()
                .withClient("inside-app")
                .secret(passwordEncoder.encode("inside-secret"))
                .authorizedGrantTypes("client_credentials")
                .scopes("all")
                .accessTokenValiditySeconds(7*24*3600); // 刷新token的有效期
        super.configure(clients);
    }

    /*
    * 配置验证管理器，UserdetalService
    * */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(jwtAccessTokenConverter());
//                        .tokenStore(redisTokenStore());
        super.configure(endpoints);
    }

    public JwtTokenStore jwtTokenStore(){
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();

        //加载私钥
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.jks");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "coinexchange".toCharArray());
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("coinexchange", "coinexchange".toCharArray()));
        return tokenConverter;
    }
//    public TokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
}
