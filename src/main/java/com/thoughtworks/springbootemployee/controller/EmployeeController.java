package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees()
                .stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(value="/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable String id) throws NoEmployeeFoundException {
        return employeeMapper.toResponse(employeeService.getEmployee(id));
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeeByGender(@RequestParam String gender) {
        return employeeService.getEmployeeWithGender(gender)
                .stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getEmployeeByPage(Integer page, Integer pageSize) {
        return employeeService.getEmployeeFromPageAndPageSize(page, pageSize)
                .stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeMapper.toResponse(employeeService.createEmployee(employeeMapper.toEntity(employeeRequest)));
    }

    @PutMapping(value="/{id}")
    public EmployeeResponse updateEmployee(@PathVariable String id, @RequestBody EmployeeRequest employeeRequest) throws NoEmployeeFoundException {
        return employeeMapper.toResponse(employeeService.updateEmployee(id, employeeMapper.toEntity(employeeRequest)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) throws NoEmployeeFoundException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(204).build();
    }
}
