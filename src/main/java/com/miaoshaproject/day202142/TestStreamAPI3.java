package com.miaoshaproject.day202142;
//终止操作

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class TestStreamAPI3 {
    List<Employee1> employees = Arrays.asList(
            new Employee1("张三",18,9999.99, Employee1.Status.FREE),
            new Employee1("李四",28,8888.99, Employee1.Status.BUSY),
            new Employee1("李四",28,8888.99,Employee1.Status.BUSY),
            new Employee1("李四",28,8888.99,Employee1.Status.BUSY),
            new Employee1("李四",28,8888.99,Employee1.Status.BUSY),
            new Employee1("王五",38,7777.99,Employee1.Status.BUSY),
            new Employee1("赵六",48,6666.99, Employee1.Status.VOCATION),
            new Employee1("田七",58,4444.99, Employee1.Status.BUSY)
    );
    /*
    *收集
    * collect(Collector c) 将流转化为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
    *
    * Collector接口中的方法的实现决定了如何对流执行收集操作（如收集到的List、Set、Map）。但是Collectors实用类
    * 提供了很多静态方法，可以方便的创建常见收集器实例，具体方法与实例如下表
    * */
    @Test
    public void test10(){
        String str = employees.stream()
                        .map(Employee1::getName)
                        .collect(Collectors.joining(",","===","==="));
        System.out.println(str);
    }
    @Test
    public void test9(){
        DoubleSummaryStatistics dss = employees.stream()
                .collect(Collectors.summarizingDouble(Employee1::getSalary));
        System.out.println(dss.getSum());
        System.out.println(dss.getAverage());
        System.out.println(dss.getMax());

    }

    //分区
    @Test
    public void test8(){
        Map<Boolean,List<Employee1>> map= employees.stream()
                                                    .collect(Collectors.partitioningBy(e->e.getSalary()>8000));
        System.out.println(map);
    }
    //多级分组
    @Test
    public void test7(){
        Map<Employee1.Status,Map<String,List<Employee1>>> map =employees.stream()
                                                                .collect(Collectors.groupingBy(Employee1::getStatus,Collectors.groupingBy(e->{
                                                                    if (((Employee1) e).getAge()<=35){
                                                                        return "青年";
                                                                    }else if (((Employee1) e).getAge()<= 50){
                                                                        return "中年";
                                                                    }else {
                                                                        return "老年";
                                                                    }
                                                                })));
        System.out.println(map);
    }
    @Test
    public void test6(){
        Map<Employee1.Status, List<Employee1>> map = employees.stream()
                                            .collect(Collectors.groupingBy(Employee1::getStatus));
        System.out.println(map);
    }

    @Test
    public void test5(){
        //总数
        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);
        System.out.println("-------------------");
        //平均值
        Double avg = employees.stream()
                                .collect(Collectors.averagingDouble(Employee1::getSalary));
        System.out.println(avg);
        System.out.println("--------------------");
        //总和
        Double sum = employees.stream().collect(Collectors.summingDouble(Employee1::getSalary));
        System.out.println(sum);
        System.out.println("----------------------");
        //最大值
        Optional<Employee1> max = employees.stream()
                .collect(Collectors.maxBy((e1,e2)->Double.compare(e1.getSalary(),e2.getSalary())));
        System.out.println(max.get());
        System.out.println("----------------------");

        //最小值
        Optional<Double> min = employees.stream()
                        .map(Employee1::getSalary)
                        .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());
    }


        @Test
    public void test4(){
        List<String> list =employees.stream()
                                        .map(Employee1::getName)
                                        .collect(Collectors.toList());
        list.forEach(System.out::println);
        System.out.println("------------------------------");
        Set<String> set = employees.stream()
                                    .map(Employee1::getName)
                                    .collect(Collectors.toSet());
        set.forEach(System.out::println);
        System.out.println("------------------------------");
        HashSet<String> hs = employees.stream()
                .map(Employee1::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hs.forEach(System.out::println);


    }

    /*
    * 归约
    * reduce(T identity, BinaryOpetator) 可以将流中元素反复结合起来,得到一个值,返回T
    * /reduce(BinaryOperator) -- 可以将流中元素反复结合起来，得到一个值，返回Optional<T>
    *备注：map和reduce的连接通常称为map-reduce模式，因为Google用它来进行网络搜索而出名。
    *
    * */
    @Test
    public void test3(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sum = list.stream()
                .reduce(0,(x,y)->x+y);
        System.out.println(sum);
        System.out.println("-----------------");
        Optional<Double> op = employees.stream()
                                        .map(Employee1::getSalary)
                                        .reduce(Double::sum);
        System.out.println(op.get());

    }





    /*
    * 查找与匹配
    * allMathch检查是否匹配所有的元素
    * anyMathch检查是否至少匹配一个元素
    * noneMathch检查是否没有匹配所有的元素
    * findFirst返回第一个元素
    * findAny返回流中的任意元素
    * count返回流中元素总个数
    * max(Consumer c)返回流中最大值
    * min(Consumer c)返回流中最小值
    * forEach(Consumer c)内部迭代（使用Collection接口需要用户去做迭代，称为外部迭代，相反，Stream API使用内部迭代--他帮你把迭代做了）
    * */
    @Test
    public void test2(){
        Long count = employees.stream()
                .count();
        System.out.println(count);

        Optional<Employee1> op1 = employees.stream()
                                .max((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary()));
        System.out.println(op1.get());

        Optional<Double> op2 = employees.stream()
                                    .map(Employee1::getSalary)
                                    .min(Double::compare);
        System.out.println(op2.get());

    }

    @Test
    public void test1(){
        boolean b1=employees.stream()
                .allMatch((e) ->e.getStatus().equals(Employee1.Status.BUSY));
        System.out.println(b1);

        boolean b2 = employees.stream()
                .anyMatch(e->e.getStatus().equals(Employee1.Status.BUSY));
        System.out.println(b2);

        boolean b3 = employees.stream()
                .noneMatch(e->e.getStatus().equals(Employee1.Status.BUSY));
        System.out.println(b3);

        Optional<Employee1> op = employees.stream()//单线程并行
                .sorted((e1,e2)-> -Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(op);

        Optional<Employee1> op1 = employees.parallelStream() //多线程并行
                .filter(e->e.getStatus().equals(Employee1.Status.FREE))
                .findAny();
        System.out.println(op1.get());

    }




}
