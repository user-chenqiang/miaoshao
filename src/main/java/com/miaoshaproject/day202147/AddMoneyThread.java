package com.miaoshaproject.day202147;

/**
 * @ClassName AddMoneyThread
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/9
 * @Version 1.0
 **/
public class AddMoneyThread implements Runnable{
    private Account account;    //存入账户
    private double money;       //存入金额

    public  AddMoneyThread(Account account,double money){
        this.account=account;
        this.money=money;
    }
    public void run(){
        account.deposit(money);
    }

}
