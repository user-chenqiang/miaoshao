package com.miaoshaproject.day202142;

import org.junit.Test;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

/*
* 重复注解与类型注解
* */
public class TestAnnotation {
    private @NonNull Object obj = null;
    @Test
    public void test1() throws NoSuchMethodException {
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method m1 =clazz.getMethod("show");
        MyAnnotation[] mas = m1.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation myAnnotation:mas){
            System.out.println(myAnnotation.value());
        }
    }


    @MyAnnotation("hello")
    @MyAnnotation("world")
    public void show(@MyAnnotation("abc") String str){}
}
