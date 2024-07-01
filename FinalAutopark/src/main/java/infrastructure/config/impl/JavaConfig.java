package infrastructure.config.impl;

import infrastructure.core.impl.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>,Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set<Class<? extends T>> interfaceRealisations = this.scanner.getSubTypesOf(target);
        if (interfaceRealisations.size() != 1) {
            throw new RuntimeException("Target interface has 0 or more than 1 implementations");
        }
        else {
            return interfaceRealisations.stream().iterator().next();
        }
    }

    @Override
    public Scanner getScanner() {
        return this.scanner;
    }
}
