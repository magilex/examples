package com.magilex.examples.java8;

import java.lang.reflect.AnnotatedElement;

import static java.lang.System.out;

/*
@Target(ElementType.METHOD)
@interface MyAnnotations {
    MyAnnotation[] value();
}
*/
@Deprecated
public class RepeatingAnnotations {
    @MyAnnotation
    @Deprecated
    String val;

    //@MyAnnotation
    //@MyAnnotation("***")
    public void foo() {

    }

    static String myAnnotationsProcessor(Object object) {
        //Method[] methods = object.getClass().getDeclaredMethods();
        //for (AnnotatedElement method : methods) {
        AnnotatedElement[] methods = object.getClass().getDeclaredFields();
        for (AnnotatedElement method : methods) {
            MyAnnotation[] annotations = method.getAnnotationsByType(MyAnnotation.class);
            out.println("anntoation present? " + method.isAnnotationPresent(MyAnnotation.class));
            for (MyAnnotation annotation : annotations) {
                out.println("Processing " + MyAnnotation.class.getSimpleName() + " with value " + annotation.value());
            }
        }

        return "";
    }

    public static void main(String[] args) {
        RepeatingAnnotations ra = new RepeatingAnnotations();
        myAnnotationsProcessor(ra);
        ra.foo();
    }
}
