package infrastructure.core.impl;

import infrastructure.config.impl.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}
