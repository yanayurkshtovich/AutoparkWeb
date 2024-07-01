package infrastructure.core.impl;

import infrastructure.config.impl.Config;
import infrastructure.config.impl.JavaConfig;

import java.util.Map;

public class ApplicationContext implements Context {
    private final Config config;
    private final Cache cache;
    private final ObjectFactory factory;


    public ApplicationContext(String packageToScan, Map<Class<?>,Class<?>> interfaceToImplementation) {
        this.config = new JavaConfig(new ScannerImpl(packageToScan), interfaceToImplementation);
        this.cache = new CacheImpl();
        cache.put(Context.class, this);
        this.factory = new ObjectFactoryImpl(this);
        }

    @Override
    public <T> T getObject(Class<T> type) {
        T object = null;
        if (cache.contains(type)) {
            return cache.get(type);
        }
        else if (type.isInterface()) {
            object = factory.createObject(config.getImplementation(type));
        }
        else {
            object = factory.createObject(type);
        }

        cache.put(type, object);
        return object;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }
}
