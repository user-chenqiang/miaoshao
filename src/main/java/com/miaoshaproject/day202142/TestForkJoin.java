package com.miaoshaproject.day202142;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {
    /*
    * forkjoin框架
    * */
    @Test
    public void test1(){
        //System.currentTimeMillis();
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0,100000000L);
        long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end =Instant.now();
        System.out.println("耗时时间为："+ Duration.between(start,end).toMillis());//14146-16587
    }
    /*
    * 普通for
    * */
    @Test
    public void test2(){
        Instant start = Instant.now();
        long sum = 0L;
        for(long i= 0; i<100000000L;i++){
            sum += i;
        }
        Instant end =Instant.now();
        System.out.println("耗时时间为："+ Duration.between(start,end).toMillis());//14146-16587
    }
    @Test
    public void test3(){
        Instant start = Instant.now();
        LongStream.rangeClosed(0,100000000000L)
                    .parallel()
                    .reduce(0,Long::sum);
        Instant end =Instant.now();
        System.out.println("耗时时间为："+ Duration.between(start,end).toMillis());//14146-16587
    }
}
