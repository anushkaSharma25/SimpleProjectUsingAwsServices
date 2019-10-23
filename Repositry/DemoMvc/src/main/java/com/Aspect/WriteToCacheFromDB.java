package com.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.dao.ConfigBeansClass;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Component
@Aspect

public class WriteToCacheFromDB {

	@Autowired
	JedisCluster newEntry;
	
	
	 @AfterReturning(pointcut="allAfter(id, name)", returning="retVal") 
	public void afterStoreData(int id, String name, boolean retVal) {
	 
		System.out.println(". Entering after advice");
		 if(retVal == true && name != "") {
			 System.out.println("inside IF");
			// Adding in Redis after storing the entry in mysql-DB
			 String myId = Integer.toString(id);
			
			  newEntry.set(myId, name);
			  System.out.println("Insertion successful inside redis cache");
			
		 }
		  
		  System.out.println("exiting after advice");	  
	  
	  }
	 
	 
	 @Pointcut("execution(public * com.dao.StudentDataDao.store*(..)) &&"+"args(id,name)")
		public void allAfter(int id, String name) {
	}
	 
	 /*
		 * AnnotationConfigApplicationContext ctx = new
		 * AnnotationConfigApplicationContext(ConfigBeansClass.class); Jedis newEntry =
		 * (Jedis)ctx.getBean("giveJedisObj");
		 * /* ctx.close(); 
	 */
	 

	/*		cannot use because my method never ends at an exception it will throw but moves forward
	 * @AfterThrowing(pointcut =
	 * "execution(public * com.dao.StudentDataDao.store*(..))", throwing = "e")
	 * public void afterError(Throwable e) { System.out.println("invalid input.");
	 * System.out.println(e.getMessage()); }
	 */

	
}
