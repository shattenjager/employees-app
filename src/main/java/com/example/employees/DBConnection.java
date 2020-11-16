/* Copyright 2015 Oracle and/or its affiliates. All rights reserved. */
package com.example.employees;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;


public class DBConnection {
    private static Connection connection = null;
    private static DBConnection instance = null;

    private DBConnection() {    	
    	
    }	

    public static DBConnection getInstance() {
        if (connection == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
        	Context ctx = null;
          try {
          	ctx = new InitialContext();
              javax.sql.DataSource ds 
                = (javax.sql.DataSource) ctx.lookup ("jdbc/testds");
              connection = ds.getConnection();
          } catch (Exception sqle) {
              sqle.printStackTrace();
          }
        }
        return connection;
    }

}
