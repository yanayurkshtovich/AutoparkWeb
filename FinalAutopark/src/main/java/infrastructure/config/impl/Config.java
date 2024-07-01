package infrastructure.config.impl;

import infrastructure.core.impl.Scanner;

public interface Config {
    <T> Class<? extends T> getImplementation(Class<T> target);
    Scanner getScanner();
}
