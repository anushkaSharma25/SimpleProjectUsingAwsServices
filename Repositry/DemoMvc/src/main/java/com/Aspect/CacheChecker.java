package com.Aspect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.aspectj.lang.JoinPoint;

import com.abstraction.CacheSoln;
import com.dao.ConfigBeansClass;
import com.model.StudentDataModel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.commands.JedisCommands;


@Component
@Aspect

public class CacheChecker {
	
	@Autowired
	CacheSoln pool, newEntry;
	
	@Autowired
	StudentDataModel student=null;
	
	 @Around("allBefore() && args(i)") 
	public StudentDataModel forCheckData(  ProceedingJoinPoint pjp, int i) {
		
		 	
			String id = Integer.toString(i);
		
			System.out.println("Entered advice");
			
		
		try {
			  		
			
					 if(pool.isPresent(id)) {
						 	
									System.out.println(pool.get(id));
									System.out.println("Present in cache");
									student.setId(i);
									student.setName(pool.get(id));		
									System.out.println(student.getName());	
					 }			 
					
					 else {
						 
						 System.out.println("Not found in cache. Searching in main db.");
						 student = (StudentDataModel)pjp.proceed();
						  
						  if (null == student) {
							  return null;
						  }
						  
				/*
				 * String h = "Inside advice. value in obj are "; h =
				 * h.concat(student.toString()); System.out.println(h);
				 */
						// Adding in Redis
//						 	if(obj.getName() != "" && obj.getId() != 0 && obj.getName() != null) {
//					
								newEntry.set(Integer.toString(student.getId()), student.getName());
								System.out.println("added in redis cache");
//						 	}
//						 	else {
//						 		System.out.println("Redis insertion unsuccessful");
//						 	}
						 	
				/* stu = obj; */
					   }
				
		 
		}
		

		catch (Throwable e) {
			System.out.println(e);
		}
		finally {
			
			/* ((ConfigurableApplicationContext) ctx).close(); */

			 System.out.println("exiting advice");
		}
		return student;
	 }
	 
	 @Pointcut("execution(public * com.dao.StudentDataDao.checkData(..))")
	 public void allBefore() {}

	
	 



		}

		