package infrastructure.configurators.impl;

import infrastructure.core.impl.Context;

public interface ProxyConfigurator {

    <T> T makeProxy(T object, Class<T> implementation, Context context) throws Exception;
}
