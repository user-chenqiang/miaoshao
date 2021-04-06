package com.miaoshaproject.day202142;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculate extends RecursiveTask<Long> {
                                        //递归
    public static final long serialVersionUID = 13465970987L;

    private long start;
    private long end;

    public static final long THREHOLD = 10000;

    public ForkJoinCalculate(long start,long end){
        this.start = start;
        this.end = end;
    }
    @Override
    protected Long compute() {
        long lenght = end - start;
        if (lenght <= THREHOLD){
           long sum = 0;
           for (long i = start;i <= end;i++){
                sum += i;
           }
           return sum;
        }else {
            long middle =(start + end) /2;
            ForkJoinCalculate left = new ForkJoinCalculate(start,middle);
            left.fork();//拆分子任务，同时压入线程队列
            ForkJoinCalculate right = new ForkJoinCalculate(middle,end);
            right.fork();//拆分子任务，同时压入线程队列
            return left.join()+right.join();

        }

    }


}
