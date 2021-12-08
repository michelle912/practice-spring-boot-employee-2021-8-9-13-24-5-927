package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
