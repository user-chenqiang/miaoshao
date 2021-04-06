package com.miaoshaproject.day202142;

import org.junit.Test;


import java.util.*;

public class TestLambda {
    //原来的匿名内部类
    @Test
    public void test() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(o1, o2);
        }
    };
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
}
    //Lambda表达式
    @Test
    public void test01(){
        Comparator<Integer> comparator =(x,y) -> Integer.compare(x,y);
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }
    List<Employee> employees = Arrays.asList(
            new Employee(101,"张三",18,9999.99),
            new Employee(102,"李四",28,8888.99),
            new Employee(103,"王五",38,7777.99),
            new Employee(104,"赵六",48,6666.99),
            new Employee(105,"田七",58,4444.99)
    );
    //需求：获取当前公司中年龄大于35岁的员工信息
    @Test
    public void test3(){
        List<Employee> list = filterEmployees(employees);
        for (Employee employee : list){
            System.out.println(employee);
        }
    }
    public List<Employee> filterEmployees(List<Employee> list){
        List<Employee> emps = new ArrayList<>();
        for (Employee emp : list){
            if (emp.getAge() >= 35){
                emps.add(emp);
            }
        }
        return emps;
    }
    //需求：获取当前员工大于5000的员工信息
    public List<Employee> filterEmployees2(List<Employee> list){
        List<Employee> emps = new ArrayList<>();
        for (Employee emp : list){
            if (emp.getSalary() >= 5000){
                emps.add(emp);
            }
        }
        return emps;
    }
    //优化方式一:策略设计模式
    @Test
    public void test4(){
        List<Employee> list = filterEmployee(employees,new FilterEmployeeByAge());
        for (Employee employee : list){
            System.out.println(employee);
        }
        System.out.println("---------------------------------------");
        List<Employee> list1 = filterEmployee(employees,new FilterEmployeeBySalary());
        for (Employee employee : list1){
            System.out.println(employee);
        }
    }

    public List<Employee> filterEmployee(List<Employee> list,MyPredicate<Employee> mp){
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : list){
            if (mp.test(employee)){
                emps.add(employee);
            }
        }
        return emps;
    }

    //优化方式二：匿名内部类
    public void test5(){
        List<Employee> list = filterEmployee(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() >= 5000;
            }
        });
        for (Employee employee :list){
            System.out.println(employee);
        }
    }
    //优化方式三：Lambda表达式
    @Test
    public void test6(){
        List<Employee> list = filterEmployee(employees,(e)->e.getSalary() <= 5000);
        list.forEach(System.out::println);
    }
    //优化方式四:stream API
    public void test7(){
        employees.stream()
                .filter((e) -> e.getSalary() >= 5000)
                .forEach(System.out::println);
        System.out.println("---------------------------------");
        employees.stream()
                 .map(Employee::getName)
                 .forEach(System.out::println);
    }
}
