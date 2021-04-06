package com.miaoshaproject.day202142;

import org.junit.Test;

import java.util.Optional;

public class Man {
    private Godness godness;

    public Man() {
    }

    public Man(Godness godness) {
        this.godness = godness;
    }

    public Godness getGodness() {
        return godness;
    }

    public void setGodness(Godness godness) {
        this.godness = godness;
    }

    @Override
    public String toString() {
        return "Man{" +
                "godness=" + godness +
                '}';
    }
    @Test
    public void test5(){
        Optional<NewMan> op = Optional.ofNullable(new NewMan());//null
        String str = getGodnessName1(op);
        System.out.println(str);
    }
    @Test
    public  String getGodnessName1(Optional<NewMan> man){
        return man.orElse(new NewMan())
                    .getGodness()
                    .orElse(new Godness("抓吧"))
                    .getName();
    }
    //需求：获取一个男人心中女神的名字
    @Test
    public  String getGodnessName(Man man){
        if(man != null){
            Godness gn = man.getGodness();
            if (gn != null){
                return gn.getName();
            }
        }
        return "抓吧";
    }
}
