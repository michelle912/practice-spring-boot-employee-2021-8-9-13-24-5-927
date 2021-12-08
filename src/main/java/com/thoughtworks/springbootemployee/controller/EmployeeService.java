package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
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

    public Employee getEmployee(Integer id) {
        return employeeRepository.findById(id);
    }


    public List<Employee> getEmployeeWithGender(String gender) {
        return Collections.emptyList();
    }

}
