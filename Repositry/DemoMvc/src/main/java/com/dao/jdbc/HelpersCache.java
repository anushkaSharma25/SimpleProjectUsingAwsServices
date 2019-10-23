package com.dao.jdbc;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class HelpersCache {

	
	
	public JedisCluster returnJedisCluster() {
		
		HostAndPort node = new HostAndPort(Constants.hostName,Constants.port);
		JedisCluster j = new JedisCluster(node);
		 
		return j;
	}
	
	public Jedis returnJedis() {
		return new Jedis(Constants.hostName,Constants.port);
	}
	
		 
}
