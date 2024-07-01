package infrastructure.threads.annotations;

import infrastructure.dto.annotations.Table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Schedule {
    int delta();
    int timeout() default Integer.MAX_VALUE;
}
