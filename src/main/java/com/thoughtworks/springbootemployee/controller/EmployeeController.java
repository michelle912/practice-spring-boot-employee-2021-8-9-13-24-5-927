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
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeRepository.create(employee);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    @PutMapping(value="/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        Employee existingReord = employeeRepository.findById(id);

        if (existingReord == null || employee == null) {
            return null;
        }

        if(employee.getName() != null) {
            existingReord.setName(employee.getName());
        }

        if(employee.getSalary() != null) {
            existingReord.setSalary(employee.getSalary());
        }

        if(employee.getAge() != null) {
            existingReord.setAge(employee.getAge());
        }
        if(employee.getGender() != null) {
            existingReord.setGender(employee.getGender());
        }
        return employeeRepository.save(existingReord);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.status(203).build();
    }
}
