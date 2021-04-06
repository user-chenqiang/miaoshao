package com.miaoshaproject.day202142;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlShowFunctionCodeStatement;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TestLambda3 {
    List<Employee> employees = Arrays.asList(
            new Employee(101,"张三",18,9999.99),
            new Employee(102,"李四",28,8888.99),
            new Employee(103,"王五",38,7777.99),
            new Employee(104,"赵六",48,6666.99),
            new Employee(105,"田七",58,4444.99)
    );
    @Test
    public void test1(){
        Collections.sort(employees,(e1,e2) ->{
            if(e1.getAge() == e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else{
                return Integer.compare(e1.getAge(), e2.getAge());
                //return -Integer.compare(e1.getAge(), e2.getAge());//倒序
            }
        });
        for(Employee emp : employees){
            System.out.println(emp);
        }
    }

    //需求：用于处理字符串
    @Test
    public void test2(){
        System.out.println(strHandler("\t\t\t 你好世界",(str) ->str.trim()));
        System.out.println(strHandler("abcdef",(str) ->str.toUpperCase()));
        System.out.println(strHandler("你好世界你好世界",(str) -> str.substring(2,5)));
    }
    public String strHandler(String str, MyFunction mf){
        return mf.getValue(str);
    }

    //需求:对于两个Long型数据进行处理
    public void test3(){
        op(100L,200L,(x,y) -> x+y);
        op(100L,200L,(x,y) -> x*y);
    }
    public void op(Long l1,Long l2,MyFunction2<Long, Long> mf){
        System.out.println(mf.getValue(l1,l2));
    }
}
