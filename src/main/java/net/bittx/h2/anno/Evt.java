package net.bittx.h2.anno;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Evt {
    String code() default "";
    String value() default "";
}
