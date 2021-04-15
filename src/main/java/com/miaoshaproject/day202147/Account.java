package com.miaoshaproject.day202147;

/**
 * @ClassName Account
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/9
 * @Version 1.0
 **/
public class Account {
    private double balance; //账户余额
    /*
    * 存款
    * @param money存入金额
    * */
    public void deposit(double money){
        double newBalance = balance + money;
        try{
            Thread.sleep(10);
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
        balance = newBalance;
    }
    public double getBalance(){
        return balance;
    }

}
