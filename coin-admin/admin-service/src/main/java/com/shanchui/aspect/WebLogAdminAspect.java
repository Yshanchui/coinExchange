package com.shanchui.aspect;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.esotericsoftware.minlog.Log;
import com.shanchui.domain.SysUserLog;
import com.shanchui.model.WebLog;
import com.shanchui.service.SysUserLogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Order(2)
@Slf4j
public class WebLogAdminAspect {

    @Autowired
    private SysUserLogService sysUserLogService;

    private Snowflake snowflake = new Snowflake(1, 1);

    /**
     * 日志记录；
     * 环绕通知：方法执行之前，之后都要切入
     */

    /**
     * 定义切入点
     */

    @Pointcut("execution(* com.shanchui.controller..*.*(..))") //controller包下的所有方法
    public void webLog() {
    }

    /**
     * 2 记录日志的环绕通知
     */

    @Around("webLog()")
    public Object  recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();
        long start = System.currentTimeMillis();

        //执行方法的真实调用
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

        long end = System.currentTimeMillis();

        webLog.setSpendTime((int) (end-start)/1000);

        //获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取安全的上下文
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String url = request.getRequestURL().toString();
        webLog.setUri(request.getRequestURI()); //设置请求的uri
        webLog.setUrl(url);
        webLog.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath())); // http://ip:port
        webLog.setUsername(authentication==null ? "anonymous" : authentication.getPrincipal().toString());//获取用户的id
        webLog.setIp(request.getRemoteAddr()); // TODO 获取用户的ip

        //获取方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取类的名称
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();

        Method method = signature.getMethod();

        //因为我们会使用swagger，必须在方法上面添加@ApiOperation(value = "xxx")注解
        //获取方法上面的注解
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);

        webLog.setDescription(annotation==null ? "no desc" : annotation.value());
        webLog.setMethod(targetClassName + "." + method.getName());
        webLog.setParameter(getMethodParameter(method,proceedingJoinPoint.getArgs())); //{"key_参数的名称": "valuer_参数的值"}
        webLog.setResult(result);

        SysUserLog sysUserLog = new SysUserLog();
        sysUserLog.setId(snowflake.nextId());
        sysUserLog.setCreated(new Date());

        sysUserLog.setDescription(webLog.getDescription());
        sysUserLog.setGroup(webLog.getDescription());
        sysUserLog.setUserId(Long.valueOf(webLog.getUsername()));
        sysUserLog.setMethod(webLog.getMethod());
        sysUserLog.setIp(webLog.getIp());
        sysUserLogService.save(sysUserLog);
        return result;
    }

    /**
     * 获取方法的参数
     *
     * @param method
     * @param args
     * @return
     */
    private Object getMethodParameter(Method method, Object[] args) {
        Map<String, Object> methodParametersWithValues = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        //方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            methodParametersWithValues.put(parameterNames[i], args[i]);
        }
        return methodParametersWithValues;
    }
}
