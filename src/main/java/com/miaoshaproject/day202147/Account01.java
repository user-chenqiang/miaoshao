package com.miaoshaproject.day202147;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Account01
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/9
 * @Version 1.0
 **/
public class Account01 {
    private Lock accountLock = new ReentrantLock();
    private double balabce; //账户余额
    /*
    * 存款
    * @param money 存入金额
    * */
    public synchronized void deposit(double money){
        accountLock.lock();
        try{
            double newBalance = balabce + money;
            try {
                Thread.sleep(10); //模拟此业务需要一段处理时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balabce = newBalance;
        }finally {
            accountLock.unlock();
        }
    }
    /*
    * 获取账户余额
    * */
    public double getBalabce(){
        return balabce;
    }
}
