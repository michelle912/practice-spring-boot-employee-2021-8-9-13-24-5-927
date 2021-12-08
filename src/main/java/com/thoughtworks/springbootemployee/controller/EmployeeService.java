package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        Employee existingReord = employeeRepository.findById(id);

        if (existingReord == null || employee == null) {
            return null;
        }

        if(employee.getSalary() != null) {
            existingReord.setSalary(employee.getSalary());
        }

        if(employee.getAge() != null) {
            existingReord.setAge(employee.getAge());
        }
        return employeeRepository.save(existingReord);
    }
}
