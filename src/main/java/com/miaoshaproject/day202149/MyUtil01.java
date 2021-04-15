package com.miaoshaproject.day202149;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @ClassName MyUtil01
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/9
 * @Version 1.0
 **/
public class MyUtil01 {
    //工具类中的方法都是静态方式访问的因此将构造器私有不允许创建对象（绝对好习惯）
    private MyUtil01(){
        throw new AssertionError();
    }
    /*
    *
    * 统计给定义文件中给定字符串的出现次数
    * @param filename 字符串
    * @param word 字符串
    * @return 字符串在文件中出现的次数
    * */
    public static int countWordInFile(String filename,String word) throws FileNotFoundException {
        int counter = 0;
        try(FileReader fr = new FileReader(filename)){
            try(BufferedReader br = new BufferedReader(fr)){
                String line = null;
                while((line = br.readLine())!=null){
                    int index = -1;
                    while (line.length() >= word.length() &&(index = line.indexOf(word))>=0){
                        counter++;
                        line = line.substring(index + word.length());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return counter;
    }
}
