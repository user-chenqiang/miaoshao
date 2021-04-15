package com.miaoshaproject.day202147;

import java.util.concurrent.Callable;

/**
 * @ClassName MyTask
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/9
 * @Version 1.0
 **/
 class MyTask implements Callable<Integer> {
    private int upperBounds;
    public MyTask(int upperBounds){
        this.upperBounds = upperBounds;
    }
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i= 1;i<=upperBounds;i++){
            sum += i;
        }
        return sum;
    }
}
