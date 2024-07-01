package infrastructure.core.impl;

import infrastructure.configurators.impl.ObjectConfigurator;
import infrastructure.configurators.impl.ProxyConfigurator;
import infrastructure.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory{
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        scanner.getSubTypesOf(ProxyConfigurator.class).stream().forEach(x -> {
            try {
                proxyConfigurators.add(x.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
        Set<Class <? extends ObjectConfigurator>> objectConfiguratorSet = scanner.getSubTypesOf(ObjectConfigurator.class);
        objectConfiguratorSet.forEach(x -> {
            try {
                objectConfigurators.add(x.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T makeProxy(Class<T> implClass, T object) throws Exception {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            object = (T) proxyConfigurator.makeProxy(object, implClass, context);
        }

        return object;
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T object = create(implementation);
        configure(object);
        initialize(implementation, object);
        object = makeProxy(implementation, object);
        return object;
    }

    private <T> T create(Class<T> implementation) throws Exception {
        return implementation.getConstructor().newInstance();
    }

    private <T> void configure(T object) {
        this.objectConfigurators.forEach(x -> x.configure(object, this.context));
    }

    private <T> void initialize(Class<T> implementation, T object) throws Exception {
        for (Method method : implementation.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }
}
