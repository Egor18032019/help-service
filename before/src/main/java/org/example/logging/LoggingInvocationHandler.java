package org.example.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class LoggingInvocationHandler implements InvocationHandler {
    private final Object target;
    private LoggerServiceImpl loggerService = new LoggerServiceImpl();

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       if(args!=null){
           System.out.println(Arrays.stream(args).toList().toString());
       }
        loggerService.log("Старт метода ! " + method.getName() + " в " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now()));
        final var result = method.invoke(target, args);
        loggerService.log("Конец метода !" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now()));
        return result;
    }
}
