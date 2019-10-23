package com.dao;

import java.sql.*;


import org.springframework.beans.factory.annotation.Autowired;

import com.model.StudentDataModel;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.jdbc.PreparedStatement;

public class JdbcImplementation {
	
	private String tableName = "Student";
	
	java.sql.PreparedStatement ps=null;
	ResultSet rs=null;
	
	@Autowired
	Connection con;
	
	@Autowired
	StudentDataModel student;
	
	
	static String q,s;
	{
		
		q="insert into ";
		q = q.concat(tableName);
		q = q.concat(" (id,name) values(?,?)");
		
		s = "select * from ";
		s = s.concat(tableName);
		s = s.concat(" where id=?");
	}
	
	public static final String insertQuery = q;
	public static final String selectQuery = s;

	
	public void insert(int id, String name) {	
		
		try {
			System.out.println(insertQuery);
			ps = con.prepareStatement(insertQuery);
			System.out.println("before executing query!");
			ps.setInt(1,id);
			ps.setString(2, name);
			System.out.println(insertQuery);
				// Adding in main database
			ps.executeUpdate();
		} 
		catch (SQLException e) {
		
			System.out.println("Unsuccessful insertion. Some sql exception exists. check console for details.");
			e.printStackTrace();
		}
		
		catch (Throwable e) {
			System.out.println("Unsuccessful insertion");
			System.out.println(e);
			//System.exit(0);;
		}
		finally {
			System.out.println("in finally section");
			try {
				
				con.close();
				

			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		  
		
	}
	
	
	public StudentDataModel select(int id) {
		Statement statement;
		try {				
			System.out.println(s);
			ps =  con.prepareStatement(s);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			System.out.println(s);
			 

			if(rs.next()) {
				
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				return student;
			}
			//System.out.println("outside if");
			
			
			 else { return null; }
			
		}
		catch(SQLException e) {
			System.out.println("Some error in fetching data:-");
			System.out.println(e);
		}
		catch(Exception e) {
			System.out.println(e);
		}

		finally {
			try {
				rs.close();
				con.close();
				//((ConfigurableApplicationContext) ctx).close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
		
		
	}
}
