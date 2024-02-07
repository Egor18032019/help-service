package org.example.context;


import org.example.configuration.Configuration;
import org.example.configuration.Instance;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ApplicationContext {

    private final Map<Class<?>, Object> storageInstances = new HashMap<>();

    public ApplicationContext() throws InvocationTargetException, IllegalAccessException {
        Reflections reflection = new Reflections("org.example.configuration");
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
                storageInstances.put(method.getReturnType(), method.invoke(configuration));
            }


        }


    }

    public <T> T getInstance(Class<T> type) {
        return (T) Optional.ofNullable(storageInstances.get(type)).orElseThrow();
    }

}
