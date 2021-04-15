package com.miaoshaproject.day202147;

/**
 * @ClassName AddMoneyThread01
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/9
 * @Version 1.0
 **/
public class AddMoneyThread01 implements Runnable{
    private Account01 account01;    //存入账户
    private double money;       //存入金额
    public AddMoneyThread01 (Account01 account01,double money){
        this.account01 = account01;
        this.money = money;
    }
    public void run(){
        synchronized (account01){
            account01.deposit(money);
        }
    }
}
