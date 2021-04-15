package com.miaoshaproject.day202147;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Test02
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/9
 * @Version 1.0
 *
 * Account
 * AddMoneyThread
 **/
public class Test02 {
    public static void main(String[] args) {
        Account01 account01 = new Account01();
        ExecutorService service = Executors.newFixedThreadPool(100);
        for(int i=1;i<=100;i++){
            service.execute(new AddMoneyThread01(account01,1));
        }
        service.shutdown();
        while (!service.isTerminated()){}
        System.out.println("账户余额："+account01.getBalabce());
    }
}
/*
* public class Test02 {
    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService service = Executors.newFixedThreadPool(100);
        for(int i=1;i<=100;i++){
            service.execute(new AddMoneyThread(account,1));
        }
        service.shutdown();
        while (!service.isTerminated()){}
        System.out.println("账户余额："+account.getBalance());
    }
}
* */
