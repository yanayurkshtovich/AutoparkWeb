package infrastructure.configurators.impl;

import infrastructure.core.impl.Context;

public interface ObjectConfigurator {
    void configure(Object object, Context context);
}
