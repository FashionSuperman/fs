package com.fashionSuperman.fs.core.component;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
@Component
public class DistributedLockService {
	@Autowired
	private JedisPool jedisPool;
	/**
	 * 获取锁
	 * @param lockName 锁的名字
	 * @param acquireTimeout 获取超时设置
	 * @param lockTimeout 锁超时设置
	 * @return  null 表示获取锁失败
	 */
	public String acquireLockWithTimeout(String lockName, long acquireTimeout, long lockTimeout) {
		Jedis conn = this.jedisPool.getResource();
		String identifier = UUID.randomUUID().toString();
		String lockKey = "lock:" + lockName;
		int lockExpire = (int) (lockTimeout / 1000);

		long end = System.currentTimeMillis() + acquireTimeout;
		while (System.currentTimeMillis() < end) {
			if (conn.setnx(lockKey, identifier) == 1) {
				conn.expire(lockKey, lockExpire);
//				this.jedisPool.returnResource(conn);
				conn.close();
				return identifier;
			}
			if (conn.ttl(lockKey) == -1) {
				conn.expire(lockKey, lockExpire);
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
		}

		// null 表示获取锁失败
//		this.jedisPool.returnResource(conn);
		conn.close();
		return null;
	}
	
	/**
	 * 释放锁
	 * @param lockName
	 * @param identifier
	 * @return
	 */
    public boolean releaseLock(String lockName, String identifier) {
    	Jedis conn = this.jedisPool.getResource();
        String lockKey = "lock:" + lockName;

        long end = System.currentTimeMillis() + 2000;
        
        while (System.currentTimeMillis() < end){
            conn.watch(lockKey);
            if (identifier.equals(conn.get(lockKey))){
                Transaction trans = conn.multi();
                trans.del(lockKey);
                List<Object> results = trans.exec();
                if (results == null){
                    continue;
                }
//                this.jedisPool.returnResource(conn);
                conn.close();
                return true;
            }
            conn.unwatch();
            break;
        }
//        this.jedisPool.returnResource(conn);
        conn.close();
        return false;
    }
}
