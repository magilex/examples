package com.magilex.examples.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by mcamacho on 10/14/15.
 */

//@Repeatable(MyAnnotations.class)
@Target(ElementType.FIELD)
public @interface MyAnnotation {
    String value() default "default";
}
