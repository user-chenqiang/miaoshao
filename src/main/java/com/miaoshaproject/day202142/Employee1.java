package com.miaoshaproject.day202142;

public class Employee1 {
    private String name;
    private Integer age;
    private double salary;
    private Status Status;


    @Override
    public String toString() {
        return "Employee1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", Status=" + Status +
                '}';
    }

    public Employee1() {
    }

    public Employee1(String name, Integer age, double salary, Status status) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        Status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status status) {
        Status = status;
    }
    public enum Status{
        FREE,
        BUSY,
        VOCATION;
    }
}

