package infrastructure.core.impl;

public interface ObjectFactory {
    <T> T createObject(Class<T> implementation);
}
