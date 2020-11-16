/* Copyright © 2015 Oracle and/or its affiliates. All rights reserved. */
package com.example.employees;

import java.util.List;


public class EmployeeService {

    EmployeePersistence persistence = new EmployeePersistence();

    public List<Employee> getAllEmployees() {       
        return persistence.findAll();
    }

    public List<Employee> searchEmployeesByName(String name) {
        return persistence.searchEmployeesByName(name);
    }

    public Employee getEmployee(long id) throws Exception {
       return persistence.findbyId(id);
    }   

    public long addEmployee(Employee employee) {
        persistence.save(employee);
        return employee.getId();
    }

    public boolean updateEmployee(Employee employee) {
       return persistence.update(employee);
    }

    public boolean deleteEmployee(long id) {
       return persistence.delete(id);
    }
}
