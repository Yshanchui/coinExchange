package com.shanchui.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${no.require.token.uris:/admin/login}")
    private Set<String> noRequireTokenUris;
    /*
    * 过滤器拦截到用户的请求后做什么
    * */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1、该接口是否需要token才能访问
        if(!isRequsetToken(exchange)){
            return chain.filter(exchange); //不需要token，直接放心
        }
        // 2、取出用户的token
        String token = getUserToken(exchange);
        // 3、校验token是否正确
        if(StringUtils.isEmpty(token)){
            return buildNoAuthorizationResult(exchange);
        }
        Boolean hasKey = stringRedisTemplate.hasKey(token);
        if(hasKey!=null && hasKey){
            return chain.filter(exchange); //token有效，直接放行
        }
        return buildNoAuthorizationResult();
    }

    /*
    * 从请求头里面获取用户的token*/
    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replace("Bearer ", "");
    }

    /*判断该接口是否需要token*/
    private boolean isRequsetToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if(noRequireTokenUris.contains(path)){
            return false; //不需要token
        }
        return Boolean.TRUE; //需要token
    }

    /*
    * 拦截去的顺序*/
    @Override
    public int getOrder() {
        return 0;
    }
}
