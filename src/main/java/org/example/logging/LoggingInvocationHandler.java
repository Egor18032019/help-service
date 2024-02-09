package org.example.logging;

import org.example.annotation.Instance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Instant;

public class LoggingInvocationHandler implements InvocationHandler {
    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Старт метода !" + Instant.now());
        final var result = method.invoke(target,args);
        System.out.println("Конец метода !" + Instant.now());
        return result;
    }
}
