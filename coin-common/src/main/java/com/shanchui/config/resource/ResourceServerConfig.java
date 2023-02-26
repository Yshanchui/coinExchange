package com.shanchui.config.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
                 http.csrf()
                         .disable()
                         .sessionManagement().disable()
                         .authorizeRequests()
                         .antMatchers( //放行路径
                                 "/login",
                                 "/v2/api-docs",
                                 "/swagger-resources/configuration/ui",
                                 "/swagger-resources",
                                 "/webjars/**",
                                 "/swagger-ui.html"
                         ).permitAll()
                         .antMatchers("/**").authenticated()
                         .and().headers().cacheControl();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer) throws Exception {
            resourceServerSecurityConfigurer.tokenStore(jwtTokenStore());
    }

    private TokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //resporce 验证token(公钥) authorization 产生token(私钥)
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.txt");
        String s = null;
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            s = new String(bytes, StandardCharsets.UTF_8);
        }catch (Exception e) {
            e.printStackTrace();
        }
        tokenConverter.setVerifierKey(s);
        return tokenConverter;
    }
}
