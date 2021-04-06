package com.miaoshaproject.day202142;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

/*
    一、Lambda表达式的基础语法：java8中引入了一个新的操作符”->“该操作称为箭头操作符或者Lambda操作符

    左侧：Lambda 表达式的参数列表
    右侧：Lambda 表达式中所需执行的功能，即Lambda体

    lambda表达式可以看作是对接口实现，也是匿名内部类的实现
    函数式接口：一个接口中只有一个抽象方法的接口

    语法格式一:无参数，无返回值
        （）-> System.out.println("Hello World！")
    语法格式二：有一个参数，并且无返回值
        (x) -> System.out.println(x)
    语法格式三：若只有一个参数，小括号可以省略不写
        x -> System.out.println(x)
    语法格式四：由两个以上的参数，有返回值，并且Lambda体中有多条语句
        Comparator<Integer> com =(x,y) ->{
            System.out.println("函数式接口");
            return Integer.compare(x,y);
        };
    语法格式五：由两个以上的参数，有返回值，并且Lambda体中有一条语句，return和大括号都可以省略不写
        Comparator<Integer> com =(x,y) -> Integer.compare(x,y);
    语法格式六：Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文或者目标对象推断出，数据类型，即”类型推断“
    Comparator<Integer> com =(Integer x,Integer y) -> Integer.compare(x,y);

    左右遇一括号省
    左侧推断类型省
    能省则省

    二、Lambda表达式需要”函数式接口“的支持
    函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。可以使用@FunctionalIterface修饰
    可以检查是不是函数式接口
*/
public class TestLambda2 {
    @Test
    public void test1(){
        int num = 0;//jdk1.7前，必须式final 现在默认加上final具体看num在使用能不能是变量num++是否报错
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world"+ num);
            }
        };
        r.run();
        System.out.println("-----------------------------");

        Runnable r1 = () -> System.out.println("Hello world");
        r1.run();
    }

    @Test
    public void test2(){
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("hello world!");
    }

    @Test
    public void test3(){
        Consumer<String> con = x -> System.out.println(x);
        con.accept("hello world!");
    }

    public void test4(){
        Comparator<Integer> com =(x,y) ->{
            System.out.println("函数式接口");
            return Integer.compare(x,y);
        };
    }

    public void test5(){
        Comparator<Integer> com =(x,y) -> Integer.compare(x,y);
    }
    @Test
    public void test6(){
//        String[] strs ;
//        strs= {"aaa","bbb","ccc"};
        List<String> list =new ArrayList<>();
        show(new HashMap<>());
    }
    public void show (Map<String,Integer> map){

    }

    //需求：对一个数进行运算
    public void test7(){
       Integer num = operation(100,(x) -> x * x);
        System.out.println(num);
        System.out.println(operation(200,(y) -> y + 200));
    }
    public Integer operation(Integer num,MyFun mf){
        return mf.getValue(num);
    }
}
