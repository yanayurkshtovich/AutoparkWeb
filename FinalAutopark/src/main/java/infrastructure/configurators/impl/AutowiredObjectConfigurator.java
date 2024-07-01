package infrastructure.configurators.impl;

import infrastructure.core.annotations.Autowired;
import infrastructure.core.impl.Context;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AutowiredObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Autowired.class)) {
                f.setAccessible(true);
                f.set(object, context.getObject(f.getType()));
            }
        }
    }
}
