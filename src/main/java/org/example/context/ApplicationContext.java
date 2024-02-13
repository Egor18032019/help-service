package org.example.context;


import org.example.annotation.Configuration;
import org.example.annotation.Controller;
import org.example.annotation.Instance;
import org.example.logging.LoggingInvocationHandler;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private final Map<Class<?>, Object> storageInstances = new ConcurrentHashMap<>();
    private final Map<String, Class<?>> storageControllers = new ConcurrentHashMap<>();


    public ApplicationContext(String packageForScan) {
        init(packageForScan);
    }

    public ApplicationContext() {
        init("org.example.configuration");
    }

    //todo вынести отдельно в методы
    // + сортировку добавить
    private void init(String packageForScan) {
        this.getConfigurations(packageForScan);
        this.setControllersInStorageControllers();
    }

    public <T> T getInstance(Class<T> type) {
        return (T) Optional.ofNullable(this.storageInstances.get(type))
                .orElseThrow(() -> new RuntimeException("No instance with such type: %s".formatted(type)));
    }

    private Object wrapWithLoggingProxy(Object object) {
        return Proxy.newProxyInstance(
//                this.getClass().getClassLoader(),
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new LoggingInvocationHandler(object)
        );
    }

    public Map<Class<?>, Object> getStorageInstances() {
        return storageInstances;
    }

    private void getConfigurations(String packageForScan) {
        Reflections reflection = new Reflections(packageForScan);
        reflection.getTypesAnnotatedWith(Configuration.class)
                .stream()
                .map(type -> {
                    try {
                        return type.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(configuration -> setStorageInstances(configuration));

    }

    private void setControllersInStorageControllers() {
// в рамках учебного проекта ограничиваю этим пакетом
        Reflections reflection = new Reflections("org.example.servlet");

        var controllers = reflection.getTypesAnnotatedWith(Controller.class).stream()
                .toList();

        for (Class<?> controller : controllers) {
            Object s = storageInstances.get(controller);

            Class<?> aClass = s.getClass();
            Controller annotation = aClass.getAnnotation(Controller.class);
            String path = annotation.path();

            storageControllers.put(path, controller);
        }
    }

    private void setStorageInstances(Object configuration) {
        List<Method> methodsAll = Arrays.stream(configuration.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Instance.class))
                .toList();

        List<Method> methodsWithoutParams = methodsAll.stream()
                .filter(method -> method.getParameters().length == 0)
                .toList();
        for (Method method : methodsWithoutParams) {
            try {
                var instanceWithLogging = wrapWithLoggingProxy(method.invoke(configuration));
                this.storageInstances.put(method.getReturnType(), instanceWithLogging);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        //todo отсортировать получить список параметров сделать (?дерево) -> получить количество связанных параметров
        // и по этому показателю отсортировать
        List<Method> methodsHaveParams = methodsAll.stream()
                .filter(method -> method.getParameters().length > 0)
                .toList();
        for (Method method : methodsHaveParams) {
            Object[] objects = Arrays.stream(method.getParameters())
                    .map(parameter -> storageInstances.get(parameter.getType()))
                    .toArray();

            try {
                var instanceWithLogging = wrapWithLoggingProxy(method.invoke(configuration, objects));
//                    this.storageInstances.put(method.getReturnType(), instanceWithLogging);
                this.storageInstances.put(method.getReturnType(), method.invoke(configuration, objects));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Map<String, Class<?>> getStorageControllers() {
        return storageControllers;
    }
}
