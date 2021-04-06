package com.miaoshaproject.day202142;


import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/*
* 一、方法引用：若Lamdba体中的内容有方法已经实现了，我们可以使用“方法引用”
*           （可以理解为方法引用是Lambda表达式的另外一种表现形式）
*
* 主要有三种语法格式：
* 对象::实例方法名
* 类::静态方法名
* 类::实例方法名
*
* 注意:
*   2、Lamdba 体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致
*   3、若Lamdba参数列表中的第一个参数是实例方法的调用者，而第二参数是实列方法的参数时，可以使用ClassName::method
*
* 二、构造器引用：
*   格式：
*   ClassName::new
* 注意:需要调用的构造器的参数列表要与函数式接口中的抽象方法的参数列表保持一致。
*
*
*
* 构造器相当于静态方法，而实例始终隐含一个this指针
*
* 三、数组引用
*   Type::new
* */
public class TestMethodRef {
    //数组的引用
    public void test7(){
        Function<Integer,String[]> fun =(x)-> new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun2 = String[]::new;
        String[] str2 = fun2.apply(20);
        System.out.println(str2.length);
    }

    //构造器引用
    public void test6(){
        Function<Integer,Employee> fun = (x) ->new Employee(x);
        Function<Integer,Employee> fun2 = Employee::new;
        Employee emp = fun2.apply(101);
        System.out.println(emp);
        BiFunction<Integer,Integer,Employee> bf = Employee::new;

    }
    public void test5(){
        Supplier<Employee> sup = ()->new Employee();
        //构造器引用方式
        Supplier<Employee> sup2 = Employee::new;
        Employee emp = sup2.get();
        System.out.println(emp);
    }

    //3类::实例方法名
    BiPredicate<String,String> bp=(x,y)->x.equals(y);
    BiPredicate<String,String> bp2=String::equals;

    //2类::静态方法名
    public void test3(){
        Comparator<Integer> com =(x,y) -> Integer.compare(x,y);
        Comparator<Integer> com1 = Integer::compare;
    }


    //对象::实例方法名
    @Test
    public void test1(){
        Consumer<String> con = x -> System.out.println(x);

        PrintStream ps1 = System.out;
        Consumer<String> con2 = x -> ps1.println(x);

                PrintStream ps = System.out;
        Consumer<String> con1 = ps::println;

        Consumer<String> con3 = System.out::println;
        con3.accept("abcdef");
    }
    @Test
    public void test2(){
        Employee emp = new Employee();
        Supplier<String> sup =() ->emp.getName();
        String str = sup.get();;
        System.out.println(str);

        Supplier<Integer> sup2=emp::getAge;
        Integer num= sup2.get();
        System.out.println(num);

    }
}
