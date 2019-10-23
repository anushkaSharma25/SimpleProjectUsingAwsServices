package com.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class HelpersMainDb {
	
	
	private static Connection con;
	
	public Connection giveMyConnection(){
		
			
			 DriverManagerDataSource dm = new DriverManagerDataSource(Constants.hostDb, Constants.userName, Constants.password);
			 dm.setDriverClassName(Constants.driverName);
			
			 try {
				 System.out.println("inside try  block");
				con = dm.getConnection();
			} catch (SQLException e) {
				
				System.out.println(e);
				e.printStackTrace();
				
			}
			
			 return con;
			 
	}

}
