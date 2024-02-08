package org.example.context;


import org.example.annotation.Configuration;
import org.example.annotation.Instance;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ApplicationContext {
    private final Map<Class<?>, Object> storageInstances = new HashMap<>();

    public ApplicationContext(String packageForScan)  {
        init(packageForScan);
    }
    public ApplicationContext()   {
        init("org.example.configuration");
    }

    private void init(String packageForScan){
        Reflections reflection = new Reflections(packageForScan);
        List<?> configurations = reflection.getTypesAnnotatedWith(Configuration.class)
                .stream()
                .map(type -> {
                    try {
                        return type.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        for (Object configuration : configurations) {
            List<Method> methods = Arrays.stream(configuration.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Instance.class))
                    .toList();
            for (Method method : methods) {
                try {
                    this.storageInstances.put(method.getReturnType(), method.invoke(configuration));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public <T> T getInstance(Class<T> type) {
        return (T) Optional.ofNullable(this.storageInstances.get(type)).orElseThrow();
    }

}
