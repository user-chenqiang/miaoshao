package com.miaoshaproject.day202142;

import org.junit.Test;

import java.util.Optional;
/*
* 常见用法：
Optional.of(T t):创建一个Optional实例；
Optional.empty():创建一个空的Optional实例；
Optional.ofNillable(T,t)若t不为空null,创建Optional实例，否则创建空实例
IsPresent（）判断是否包含值
OrElse（T t）如果调用对象包含值，返回该值，否则返回，获取的值t
OrElseGet（Supplier s）如果调用对象包含值，返回该值，否则返回s获取的值
Map(function f)如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
flatMap(Function mapper) 与map类似，要求返回值必须是Optional；
*
* */
public class TestOptional {
    @Test
    public void test6(){
        Optional<Employee> op =Optional.ofNullable(new Employee(101,"张三",18,9999.99));
//        Optional<String> str = op.map((e)-> e.getName());
//        System.out.println(str.get());
        Optional<String> str2 = op.flatMap((e)->Optional.of(e.getName()));
        System.out.println(str2);
    }
    @Test
    public void test5(){
        Optional<Employee> op = Optional.ofNullable(null);
        Employee emp = op.orElseGet(()-> new Employee());
        System.out.println(emp);
    }
    public void test4(){
        Optional<Employee> op = Optional.ofNullable(new Employee());//null
        Employee emp = op.orElse(new Employee(101,"张三",18,9999.99));
        System.out.println(emp);//Optional.ofNullable有值输出值为null输出orelse中默认值
    }
    public void test3(){
        Optional<Employee> op = Optional.ofNullable(null);
        if(op.isPresent()){
        System.out.println(op.get());}
    }
    public void test2() {
        Optional<Employee> op = Optional.empty();
        System.out.println(op.get());
    }
    public void test1() {
        Optional<Employee> op = Optional.of(null);
        Employee emp = op.get();
        System.out.println(emp);
    }

}
