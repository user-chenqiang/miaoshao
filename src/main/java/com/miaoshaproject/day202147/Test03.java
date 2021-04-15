package com.miaoshaproject.day202147;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName Test03
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/9
 * @Version 1.0
 **/
public class Test03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Future<Integer>> list = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0;i<10;i++){
            list.add(service.submit(new MyTask((int) (Math.random()*100))));
        }
        int sum =0;
        for (Future<Integer> future:list){
            //whice(!future.isDone)
            sum += future.get();
        }
        System.out.println(sum);
    }
}
