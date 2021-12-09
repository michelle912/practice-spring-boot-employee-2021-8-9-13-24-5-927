package com.thoughtworks.springbootemployee.service;


import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        employeeList.add(new Employee(1, "Tom", 20, "male", 10000,1));

        // when
        doReturn(employeeList).when(employeeRepository).findAll();
        List<Employee> actual = employeeService.getAllEmployees();

        // then
        assertEquals(employeeList, actual);

    }

    @Test
    public void should_return_empty_list_when_getAllEmployees_given_no_employees() {
        // given

        // when
        doReturn(Collections.emptyList()).when(employeeRepository).findAll();
        List<Employee> actual = employeeService.getAllEmployees();

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void should_update_employee_when_updateEmployee_given_id_and_employee() throws Exception{
        // given
        Integer id = 1;
        Employee employee = new Employee(1, "Tom", 20, "male", 10000,1);
        Employee updatedEmployee = new Employee(1, "Tom", 50, "male", 8000,1);

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
    public void should_throw_exception_when_updateEmployee_given_employee_not_exist() throws Exception{
        // given
        Integer id = 1;
        Employee employee = new Employee(1, "Tom", 20, "male", 10000,1);

        // when
        doThrow(NoEmployeeFoundException.class).when(employeeRepository).findById(id);

        // then
        assertThrows(NoEmployeeFoundException.class, () -> employeeService.updateEmployee(id, employee));


    }

    @Test
    public void should_get_correct_employee_when_get_employee_by_id_given_id() throws Exception{
        // given
        Integer id = 1;
        Employee employee = new Employee(id, "Tom", 20, "male", 10000,1);

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
    public void should_throw_exception_when_get_employee_by_id_given_not_exist() throws Exception{
        // given
        Integer id = 1;
        Employee employee = new Employee(id, "Tom", 20, "male", 10000,1);

        // when
        doThrow(NoEmployeeFoundException.class).when(employeeRepository).findById(id);

        // then
        assertThrows(NoEmployeeFoundException.class, () -> employeeService.getEmployee(id));

    }

    @Test
    public void should_get_correct_employee_with_gender_when_get_employee_by_gender_given_gender() {
        // given
        String gender = "male";
        Employee employee = new Employee(1, "Tom", 20, gender, 10000,1);

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
    public void should_return_empty_list_with_gender_when_get_employee_by_gender_given_no_record_exist() {
        // given
        String gender = "male";
        Employee employee = new Employee(1, "Tom", 20, gender, 10000,1);

        // when
        doReturn(Collections.emptyList()).when(employeeRepository).findByGender(gender);

        List<Employee> actual = employeeService.getEmployeeWithGender(gender);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void should_get_correct_page_when_get_employee_by_page_given_page_and_pagesize()  {
        // given
        Integer page = 1;
        Integer pageSize = 2;
        Employee employee3 = new Employee(3, "Tom3", 20, "male", 10000,1);
        Employee employee4 = new Employee(4, "Tom4", 20, "male", 10000,1);
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
    public void should_return_empty_list_when_get_employee_by_page_given_invalid_page_and_pagesize()  {
        // given
        Integer page = 1;
        Integer pageSize = 2;

        // when
        doReturn(Collections.emptyList()).when(employeeRepository).findByPage(page, pageSize);

        List<Employee> actual = employeeService.getEmployeeFromPageAndPageSize(page, pageSize);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void should_return_employee_when_create_given_employee() {
        // given
        Employee employee = new Employee(1, "Tom", 20, "male", 10000,1);
        employee.setId(null);
        Employee createdEmployee = new Employee(1, "Tom", 20, "male", 10000,1);


        // when
        doReturn(createdEmployee).when(employeeRepository).create(employee);

        Employee actual = employeeService.createEmployee(employee);

        // then
        assertAll(
                () -> assertEquals(createdEmployee.getId(), actual.getId()),
                () -> assertEquals(createdEmployee.getName(), actual.getName()),
                () -> assertEquals(createdEmployee.getAge(), actual.getAge()),
                () -> assertEquals(createdEmployee.getGender(), actual.getGender()),
                () -> assertEquals(createdEmployee.getSalary(), actual.getSalary())
        );

    }

    @Test
    public void should_return_nothing_when_delete_given_id() throws Exception {
        // given
        Integer id = 1;

        // when
        employeeService.deleteEmployee(id);

        // then
        Mockito.verify(employeeRepository, times(1)).deleteById(id);


    }

    @Test
    public void should_throw_exception_when_delete_given_employee_not_exist() throws Exception {
        // given
        Integer id = 1;

        // when
        doThrow(NoEmployeeFoundException.class).when(employeeRepository).deleteById(id);

        // then
        assertThrows(NoEmployeeFoundException.class, () -> employeeService.deleteEmployee(id));


    }


}
