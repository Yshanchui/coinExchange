package com.shanchui.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
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
        Boolean hasToken = stringRedisTemplate.hasKey(token);
        if(hasToken !=null && hasToken){
            return chain.filter(exchange); //token有效，直接放行
        }
        return buildNoAuthorizationResult(exchange);
    }

    /*给用户响应一个没有token的错误*/
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error","NoAuthorization");
        jsonObject.put("errorMsg","Token is Null or Error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));
    }

    /*
    * 从请求头里面获取用户的token*/
    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replace("bearer ", "");
    }

    /*判断该接口是否需要token*/
    private boolean isRequsetToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        return !noRequireTokenUris.contains(path);
    }

    /*
    * 拦截去的顺序*/
    @Override
    public int getOrder() {
        return 0;
    }
}
