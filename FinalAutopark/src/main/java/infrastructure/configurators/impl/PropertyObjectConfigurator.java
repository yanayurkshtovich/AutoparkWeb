package infrastructure.configurators.impl;

import infrastructure.core.annotations.Property;
import infrastructure.core.impl.Context;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyObjectConfigurator implements ObjectConfigurator {
    private final Map<String,String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader().getResource("application.properties");
        if (path == null) {
            throw new FileNotFoundException(String.format("File '%s' not found", "application properties"));
        }
        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream())).lines();
        properties = lines.map(line -> line.split("=")).collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Property.class)) {
                f.setAccessible(true);
                if (properties.get(f.getAnnotation(Property.class).value()).isBlank()) {
                    f.set(object, properties.get(f.getName()));
                }
                else {
                    f.set(object, properties.get(f.getAnnotation(Property.class).value()));
                }
            }
        }
    }
}
