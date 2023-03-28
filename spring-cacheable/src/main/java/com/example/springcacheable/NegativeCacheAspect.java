package com.example.springcacheable;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.*;
import org.springframework.context.expression.*;
import org.springframework.core.*;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.*;
import org.springframework.stereotype.*;

import java.lang.reflect.*;

@Component
@Aspect
public class NegativeCacheAspect  {

    @Autowired
    CacheManager cacheManager;

    @Pointcut("@annotation(com.example.springcacheable.NegativeCacheable)")
    public void negativeCache() {
    }

    @Around("negativeCache()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 准备计算缓存相关信息
        Signature signature = joinPoint.getSignature();
        Class targetClass = signature.getDeclaringType();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        NegativeCacheable cacheable = method.getAnnotation(NegativeCacheable.class);
        String key = generateKey(cacheable.key(), method, targetClass, joinPoint.getTarget(), joinPoint.getArgs());
        Throwable originalException = null;
        Cache cache = cacheManager.getCache(cacheable.value());
        try {
            Object response = joinPoint.proceed();
            if (response != null) {
                cache.put(key, response);
            }

            return response;
        } catch (Exception e) {
            originalException = e;
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null && valueWrapper.get() != null) {
                return valueWrapper.get();
            }
        }

        throw originalException;
    }

    private String generateKey(String keyExpression, Method method, Class targetClass, Object targetObject, Object[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        RootObject rootObject = new RootObject(targetClass, method, targetObject, args);
        MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(rootObject, method , args, new DefaultParameterNameDiscoverer());
        Expression expression = parser.parseExpression(keyExpression);
        String key = String.valueOf(expression.getValue(context));

        return key;
    }

    private static class RootObject {
        private final Method method;
        private final Object[] args;
        private final Object target;
        private final Class<?> targetClass;
        private final String methodName;

        public RootObject(Class<?> targetClass, Method method, Object target, Object[] args) {
            this.method = method;
            this.args = args;
            this.target = target;
            this.targetClass = targetClass;
            this.methodName = targetClass.getSimpleName() + "." + method.getName();
        }

        public Method getMethod() {
            return method;
        }

        public Object[] getArgs() {
            return args;
        }

        public Object getTarget() {
            return target;
        }

        public Class<?> getTargetClass() {
            return targetClass;
        }

        public String getMethodName() {
            return methodName;
        }
    }

}
