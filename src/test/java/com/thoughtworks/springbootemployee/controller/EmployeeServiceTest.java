package com.thoughtworks.springbootemployee.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        // when
        doReturn(employeeList).when(employeeRepository).findAll();
        List<Employee> actual = employeeService.getAllEmployees();

        // then
        assertEquals(employeeList, actual);

    }

    @Test
    public void should_update_employee_when_updateEmployee_given_id_and_employee() {
        // given
        Integer id = 1;
        Employee employee = new Employee(1, "Tom", 20, "male", 10000);
        Employee updatedEmployee = new Employee(1, "Tom", 50, "male", 8000);

        // when
        doReturn(employee).when(employeeRepository).findById(id);
        employee.setAge(50);
        employee.setSalary(8000);
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        Employee actual = employeeService.updateEmployee(id, employee);

        // then
        assertAll(
                () -> assertEquals(updatedEmployee.getId(), actual.getId()),
                () -> assertEquals(updatedEmployee.getName(), actual.getName()),
                () -> assertEquals(updatedEmployee.getAge(), actual.getAge()),
                () -> assertEquals(updatedEmployee.getGender(), actual.getGender()),
                () -> assertEquals(updatedEmployee.getSalary(), actual.getSalary())
        );


    }

    @Test
    public void should_get_correct_employee_when_get_employee_by_id_given_id() {
        // given
        Integer id = 1;
        Employee employee = new Employee(id, "Tom", 20, "male", 10000);

        // when
        doReturn(employee).when(employeeRepository).findById(id);
        Employee actual = employeeService.getEmployee(id);

        // then
        assertAll(
                () -> assertEquals(employee.getId(), actual.getId()),
                () -> assertEquals(employee.getName(), actual.getName()),
                () -> assertEquals(employee.getAge(), actual.getAge()),
                () -> assertEquals(employee.getGender(), actual.getGender()),
                () -> assertEquals(employee.getSalary(), actual.getSalary())
        );

    }

    @Test
    public void should_get_correct_employee_with_gender_when_get_employee_by_gender_given_gender() {
        // given
        String gender = "male";
        Employee employee = new Employee(1, "Tom", 20, gender, 10000);

        // when
        doReturn(Arrays.asList(employee)).when(employeeRepository).findByGender(gender);

        List<Employee> actual = employeeService.getEmployeeWithGender(gender);

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(employee.getId(), actual.get(0).getId()),
                () -> assertEquals(employee.getName(), actual.get(0).getName()),
                () -> assertEquals(employee.getAge(), actual.get(0).getAge()),
                () -> assertEquals(employee.getGender(), actual.get(0).getGender()),
                () -> assertEquals(employee.getSalary(), actual.get(0).getSalary())
        );

    }

    @Test
    public void should_get_correct_page_when_get_employee_by_page_given_page_and_pagesize()  {
        // given
        Integer page = 1;
        Integer pageSize = 2;
        Employee employee3 = new Employee(3, "Tom3", 20, "male", 10000);
        Employee employee4 = new Employee(4, "Tom4", 20, "male", 10000);
        List<Employee> employeeList = Arrays.asList(employee3, employee4);

        // when
        doReturn(employeeList).when(employeeRepository).findByPage(page, pageSize);

        List<Employee> actual = employeeService.getEmployeeFromPageAndPageSize(page, pageSize);

        // then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertEquals(employeeList.get(0).getId(), actual.get(0).getId()),
                () -> assertEquals(employeeList.get(0).getName(), actual.get(0).getName()),
                () -> assertEquals(employeeList.get(0).getAge(), actual.get(0).getAge()),
                () -> assertEquals(employeeList.get(0).getGender(), actual.get(0).getGender()),
                () -> assertEquals(employeeList.get(0).getSalary(), actual.get(0).getSalary()),
                () -> assertEquals(employeeList.get(1).getId(), actual.get(1).getId()),
                () -> assertEquals(employeeList.get(1).getName(), actual.get(1).getName()),
                () -> assertEquals(employeeList.get(1).getAge(), actual.get(1).getAge()),
                () -> assertEquals(employeeList.get(1).getGender(), actual.get(1).getGender()),
                () -> assertEquals(employeeList.get(1).getSalary(), actual.get(1).getSalary())
        );

    }

    @Test
    public void should_return_employee_when_create_given_employee() {
        // given
        Employee employee = new Employee(1, "Tom", 20, "male", 10000);
        employee.setId(null);
        Employee createdEmployee = new Employee(1, "Tom", 20, "male", 10000);


        // when
        doReturn(createdEmployee).when(employeeRepository).create(employee);

        Employee actual = employeeService.createEmployee(employee);

        // then
        assertAll(
                () -> assertEquals(employee.getId(), actual.getId()),
                () -> assertEquals(employee.getName(), actual.getName()),
                () -> assertEquals(employee.getAge(), actual.getAge()),
                () -> assertEquals(employee.getGender(), actual.getGender()),
                () -> assertEquals(employee.getSalary(), actual.getSalary())
        );

    }


}
