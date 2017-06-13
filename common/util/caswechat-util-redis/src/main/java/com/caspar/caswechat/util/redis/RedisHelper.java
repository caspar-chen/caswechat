package com.caspar.caswechat.util.redis;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis 工具类
 * @author caspar.chen
 * @version 1.0.0, 2016-9-26
 * @see RedisTemplate
 */
public class RedisHelper {

	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 是否在set时再次读取redis并返回value,默认false
	 */
	private boolean returnSetValue = false;

	public String setString(final String k, final String v) {
		return (String) redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(serialize(k), serialize(v));
				if (returnSetValue) {
					return deserialize(connection.get(serialize(k)));
				}
				return null;
			}
		});
	}

	public String setString(final String k, final String v, final long expires) {
		return (String) redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] keyBytes = serialize(k);
				connection.set(keyBytes, serialize(v));
				connection.pExpire(keyBytes, expires);
				if (returnSetValue) {
					return deserialize(connection.get(serialize(k)));
				}
				return null;
			}
		});
	}

	public String setStringUntil(final String k, final String v,
			final long timestmp) {
		return (String) redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] keyBytes = serialize(k);
				connection.set(keyBytes, serialize(v));
				connection.expireAt(keyBytes, timestmp);
				if (returnSetValue) {
					return deserialize(connection.get(serialize(k)));
				}
				return null;
			}
		});
	}

	public String setString(final String k, final int second, final String v) {
		return (String) redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(serialize(k), second, serialize(v));
				if (returnSetValue) {
					return deserialize(connection.get(serialize(k)));
				}
				return null;
			}
		});
	}

	public String getString(final String k) {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] bytes = connection.get(serialize(k));
				if (bytes == null) {
					return null;
				}
				return deserialize(bytes);
			}
		});
	}

	public Long deleteKey(final String... keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				if (ArrayUtils.isEmpty(keys)) {
					return 0L;
				}
				if (keys.length == 1) {
					return connection.del(serialize(keys[0]));
				}
				return connection.del(serializeArray(keys));
			}
		});
	}

	public Long deleteKeys(final String pattern) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Set<byte[]> keySet = connection.keys(serialize(pattern));
				if (CollectionUtils.isEmpty(keySet)) {
					return 0L;
				}
				byte[][] bytesArray = new byte[keySet.size()][];
				keySet.toArray(bytesArray);
				return connection.del(bytesArray);
			}
		});
	}

	public Long deleteKeyByPrefix(String s) {
		s = s + "*";
		return deleteKeys(s);
	}

	public Long deleteKeyByContain(String s) {
		s = "*" + s + "*";
		return deleteKeys(s);
	}

	/**
	 * 序列化,不能为空白
	 * 
	 * @param str
	 * @return
	 */
	public byte[] serialize(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		} else {
			return redisTemplate.getStringSerializer().serialize(str);
		}
	}

	public byte[][] serializeArray(String[] array) {
		if (ArrayUtils.isEmpty(array)) {
			return null;
		}

		byte[][] bytesArray = new byte[array.length][];

		for (int i = 0; i < array.length; i++) {
			bytesArray[i] = serialize(array[i]);
		}

		return bytesArray;

	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public String deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		return redisTemplate.getStringSerializer().deserialize(bytes);
	}

	public RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setReturnSetValue(boolean returnSetValue) {
		this.returnSetValue = returnSetValue;
	}

}
