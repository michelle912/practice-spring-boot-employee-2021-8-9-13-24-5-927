package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(value="/{id}")
    public Employee getEmployeeById(@PathVariable String id) throws NoEmployeeFoundException {
        return employeeService.getEmployee(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeeByGender(@RequestParam String gender) {
        return employeeService.getEmployeeWithGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeeByPage(Integer page, Integer pageSize) {
        return employeeService.getEmployeeFromPageAndPageSize(page, pageSize);
    }

    @PostMapping()
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    @PutMapping(value="/{id}")
    public Employee updateEmployee(@PathVariable String id, @RequestBody Employee employee) throws NoEmployeeFoundException {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) throws NoEmployeeFoundException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(204).build();
    }
}
