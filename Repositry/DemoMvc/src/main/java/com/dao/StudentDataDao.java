package com.dao;

import java.sql.Connection;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.Aspect.CacheChecker;
import com.model.StudentDataModel;
import com.telusko.AddController;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class StudentDataDao {
	
	@Autowired
	Connection con;
	
	@Autowired
	JdbcImplementation impl;
	
	@Autowired
	StudentDataModel student=null;
	
	ResultSet rs = null;
	Statement s=null;

	public StudentDataModel checkData(int id) {		
		
		//CASE WHERE NOT PRESENT IN REDIS
		student = impl.select(id);
		return student;
		
	}

	public Boolean storeData(int i, String name) {
		
		
		Boolean retVal = false;
		String id = Integer.toString(i);		
		System.out.println(" inside storeData()");
		
		if(name != "") {
			
					impl.insert(i,name);
					System.out.println("Added in mysql!");
					retVal = true;
		}
		
		else {
			System.out.println("Name cannot be left empty. Insertion not possible. ");
		}
		
		return retVal;
	}

}

