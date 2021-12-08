package com.thoughtworks.springbootemployee.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    public void should_get_all_employees_when_getAllEmployees_given_employees() {
        // given
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Tom", 20, "male", 10000));
        doReturn(employeeList).when(employeeRepository).findAll();

        // when
        List<Employee> actual = employeeService.findAll();

        // then
        assertEquals(employeeList, actual);

    }

}
