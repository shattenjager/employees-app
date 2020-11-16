/* Copyright 2015 Oracle and/or its affiliates. All rights reserved. */
package com.example.employees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class EmployeePersistence {

    private Connection conn = null;

    public EmployeePersistence() {
        conn = DBConnection.getInstance().getConnection();

    }

    public boolean save(Employee employee) {
        String insertTableSQL = "INSERT INTO EMPLOYEE "
                + "(ID, FIRSTNAME, LASTNAME, EMAIL, BIRTHDATE, DEPARTMENT, TITLE) "
                + "VALUES(EMPLOYEE_SEQ.NEXTVAL,?,?,?,?,?,?)";
        boolean result = false;
        try (PreparedStatement preparedStatement = this.conn
                .prepareStatement(insertTableSQL)) {

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getBirthDate());
            preparedStatement.setString(5, employee.getDepartment());
            preparedStatement.setString(6, employee.getRole());

            result = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
        return result;
    }

    public boolean update(Employee employee) {
        String updateTableSQL = "UPDATE EMPLOYEE SET FIRSTNAME= ?, LASTNAME= ?,   EMAIL=?,  BIRTHDATE=?, DEPARTMENT=?, TITLE=?  WHERE ID=?";
        boolean result = false;
        try (PreparedStatement preparedStatement = this.conn
                .prepareStatement(updateTableSQL);) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getBirthDate());
            preparedStatement.setString(5, employee.getDepartment());
            preparedStatement.setString(6, employee.getRole());
            preparedStatement.setLong(7, employee.getId());

            result = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
           
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return result;
    }

    public boolean delete(long id) {
        String deleteRowSQL = "DELETE FROM EMPLOYEE WHERE ID=?";
        boolean result = false;
        try (PreparedStatement preparedStatement = this.conn
                .prepareStatement(deleteRowSQL)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return result;
    }

    public List<Employee> findAll() {
        String queryStr = "SELECT * FROM EMPLOYEE";
        return this.query(queryStr);
    }
    
    public List<Employee> searchEmployeesByName(String name) {
        String queryStr = "SELECT * FROM EMPLOYEE WHERE LASTNAME ='"+name+"' OR FIRSTNAME='"+name+"'";
        return this.query(queryStr);
    }

    public Employee findbyId(long id) {
        String queryStr = "SELECT * FROM EMPLOYEE WHERE ID=" + id;
        Employee employee = null;
        List <Employee> employees = this.query(queryStr);
        if (employees != null && employees.size() > 0) {
            employee = employees.get(0);
        }
        return employee;
    }

    public List<Employee> query(String sqlQueryStr) {
        List<Employee> cList = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQueryStr)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cList.add(new Employee(rs.getLong("ID"), rs
                        .getString("FIRSTNAME"), rs.getString("LASTNAME"), rs
                        .getString("BIRTHDATE"), rs.getString("TITLE"), rs
                        .getString("DEPARTMENT"), rs.getString("EMAIL")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cList;

    }
}
