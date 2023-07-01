package etu2029.framework.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Annotation {
    public String Url() default"";
}
