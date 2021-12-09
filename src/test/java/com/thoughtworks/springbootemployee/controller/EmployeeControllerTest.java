package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @BeforeEach
    void setUp(){
        employeeRepository.deleteAll();
    }

    @Test
    public void should_get_all_employees_when_getAllEmployees_given_employees() throws Exception {
        // given
        Employee employee = new Employee("1", "Tom", 20, "male", 10000,"1");
        employeeRepository.save(employee);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"));

    }

    @Test
    public void should_return_employee_when_create_given_employee() throws Exception {
        // given
        String employee = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Lily1\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"salary\": 8000\n" +
                "    }";


        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Lily1"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Female"));

    }

    @Test
    public void should_get_correct_employee_when_get_employee_by_id_given_id() throws Exception {
        // given
        Employee employee = new Employee("1", "Tom", 20, "male", 10000,"1");
        employeeRepository.save(employee);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}" , employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"));

    }

    @Test
    public void should_get_correct_employee_with_gender_when_get_employee_by_gender_given_gender() throws Exception {
        // given
        Employee employee = new Employee("1", "Tom", 20, "male", 10000,"1");
        employeeRepository.save(employee);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees").param("gender", "male"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"));

    }

    @Test
    public void should_get_correct_page_when_get_employee_by_page_given_page_and_pagesize() throws Exception {
        // given
        Employee employee1 = new Employee("1", "Tom1", 20, "male", 10000,"1");
        Employee employee2 = new Employee("2", "Tom2", 20, "male", 10000,"1");
        Employee employee3 = new Employee("3", "Tom3", 20, "male", 10000,"1");
        Employee employee4 = new Employee("4", "Tom4", 20, "male", 10000,"1");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees").param("page", "1").param("pageSize" , "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Tom3"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Tom4"))
                .andExpect(jsonPath("$[1].age").value(20))
                .andExpect(jsonPath("$[1].gender").value("male"));

    }

    @Test
    public void should_update_employee_when_updateEmployee_given_id_and_employee() throws Exception {
        // given
        Employee employee = new Employee("1", "Tom", 20, "male", 10000,"1");
        employeeRepository.save(employee);

        String updateEmployee = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Tom\",\n" +
                "        \"age\": 50,\n" +
                "        \"gender\": \"male\",\n" +
                "        \"salary\": 8000\n" +
                "    }";

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(50))
                .andExpect(jsonPath("$.gender").value("male"));

    }

    @Test
    public void should_return_nothing_when_delete_given_id() throws Exception {
        // given
        Employee employee = new Employee("1", "Tom", 20, "male", 10000,"1");
        employeeRepository.save(employee);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}" , employee.getId()))
                .andExpect(status().isNoContent());

    }

}