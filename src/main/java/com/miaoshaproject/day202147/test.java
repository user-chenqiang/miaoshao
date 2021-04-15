package com.miaoshaproject.day202147;

/**
 * @ClassName test
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/8
 * @Version 1.0
 **/
public class test {
    public static  void main(String[] args) {
        String x= "abcdefg";
        String b = reverse(x);
        System.out.println(b);
    }


    public static String reverse(String originStr){
       if(originStr == null ||originStr.length()<=1)
           return originStr;
            System.out.println("____________________");
            System.out.println(originStr.substring(1));
            System.out.println(originStr.charAt(0));
           return reverse(originStr.substring(1))+originStr.charAt(0);



    }
}
