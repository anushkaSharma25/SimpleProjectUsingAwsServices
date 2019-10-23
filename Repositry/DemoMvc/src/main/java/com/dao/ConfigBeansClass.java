package com.dao;

import java.sql.Connection;

import com.dao.jdbc.Constants;
import com.dao.jdbc.HelpersCache;
import com.dao.jdbc.HelpersMainDb;
import com.Aspect.WriteToCacheFromDB;
import com.abstraction.CacheUsingEcCluster;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;


import com.Aspect.CacheChecker;
import com.model.StudentDataModel;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAspectJAutoProxy

public class ConfigBeansClass {


	@Bean
	public StudentDataDao giveLogic() {
				
		//object of class giveLogic is returned which has the whole db logic
		return new StudentDataDao();
		
	}
	
	@Bean 
	public StudentDataModel giveStuObj() {
		return new StudentDataModel();
	}
	
	
	
	 @Bean 
	 public CacheChecker checkRedisBefore() { 
		 return new CacheChecker(); 
		 
	 }
	
	 @Bean 
	 public WriteToCacheFromDB StoreInRedisAfter() { 
		 return new WriteToCacheFromDB();
	 }
	 
	/*
	 * @Bean public Jedis giveJedisObj() { return new HelpersCache().returnJedis();
	 * }
	 */
	
	 
	 @Bean
	 public JedisCluster giveJedisPoolObj() {
		 return new HelpersCache().returnJedisCluster();
	 }
	 
	 @Bean
	 public Connection giveConnection() {		 
		return new HelpersMainDb().giveMyConnection();		 
	 }
	 
	 @Bean
	 public JdbcImplementation giveJdbcImpl() {
		 return new JdbcImplementation();
	 }
	
	 @Bean
	 public CacheUsingEcCluster giveCacheUsingEcClusterObj() {
		 return new CacheUsingEcCluster();
	 }
	 
}
