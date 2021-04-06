package com.miaoshaproject.day202142;

public class FilterEmployeeBySalary implements MyPredicate<Employee>{

    @Override
    public boolean test(Employee employee) {
        return employee.getSalary() >= 5000;
    }
}
