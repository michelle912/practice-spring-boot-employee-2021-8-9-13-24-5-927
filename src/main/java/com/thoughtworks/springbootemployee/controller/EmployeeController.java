package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping(value="/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeeByGender(@RequestParam String gender) {
        return employeeRepository.findByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeeByPage(Integer page, Integer pageSize) {
        return employeeRepository.findByPage(page, pageSize);
    }

    @PostMapping()
    public ResponseEntity<Employee> createEmployee(@RequestParam Employee employee) {
        Employee createdEmployee = employeeRepository.create(employee);
        return ResponseEntity.status(201).body(createdEmployee);
    }
    
}
