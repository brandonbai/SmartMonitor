package com.github.brandonbai.smartmonitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void stringTest() {
        final String key = "abc";
        redisTemplate.opsForValue().set(key, "def");
        Object o = redisTemplate.opsForValue().get(key);
        System.out.println(o);
    }

    @Test
    public void hashTest() {
        final String key = "obj";
        redisTemplate.opsForHash().put(key, "name", "xiaobai");
        redisTemplate.opsForHash().put(key, "age", "24");
        Object o = redisTemplate.opsForHash().get(key, "name");
        System.out.println(o);
    }

    @Test
    public void setTest() {
        final String key = "set";
        redisTemplate.opsForSet().add(key, "1", "2", "3", "4", "5");
        Object o = redisTemplate.opsForSet().members(key);
        System.out.println(o);
    }

    @Test
    public void zSetTest() {
        final String key = "zset";
        redisTemplate.opsForZSet().add(key, "1", 1);
        redisTemplate.opsForZSet().add(key, "2", 2);
        Object o1 = redisTemplate.opsForZSet().range(key, 0, 1);
        Object o2 = redisTemplate.opsForZSet().rangeByScore(key, 1, 2);
        System.out.println(o1);
        System.out.println(o2);
    }

    @Test
    public void listTest() {
        final String key = "list";
        redisTemplate.opsForList().rightPush(key, "a");
        redisTemplate.opsForList().rightPush(key, "b");
        Object o = redisTemplate.opsForList().leftPop(key);
        System.out.println(o);
    }

    @Test
    public void geoTest() {
        final String key = "company";
        redisTemplate.opsForGeo().geoAdd(key, new RedisGeoCommands.GeoLocation<>("home", new Point(116.30951732397079468, 40.05753808116615033)));
        redisTemplate.opsForGeo().geoAdd(key, new Point(116.47473067045211792, 39.89852234923503005), "work");
        Distance dist = redisTemplate.opsForGeo().geoDist(key, "home", "work", Metrics.KILOMETERS);
        System.out.println(dist.getValue());
    }

    @Test
    public void hyperLogLogTest() {
        final String key = "hyperLogLog";
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(key, "user" + i);
        }
        long total = redisTemplate.opsForHyperLogLog().size(key);
        System.out.printf("%d %d\n", 100000, total);
    }

    @Test
    public void bitMapTest() {
        final String key = "bitmap";
        redisTemplate.opsForValue().setBit(key, 1, true);
        redisTemplate.opsForValue().setBit(key, 2, true);
        redisTemplate.opsForValue().setBit(key, 4, true);
        Object o = redisTemplate.opsForValue().get(key);
        System.out.println(o);

        redisTemplate.execute((RedisConnection connection) -> {
                Jedis jedis = (Jedis) connection.getNativeConnection();
            Long result = jedis.bitcount(key);
            System.out.println(result);
            return null;
            }
        );

    }

    @Test
    public void nxTest() {
        final String key = "nxKey";
        redisTemplate.execute((RedisConnection connection) -> {
                    Jedis jedis = (Jedis) connection.getNativeConnection();
                    String result = jedis.set(key, "a", "NX", "EX", 8000);
                    System.out.println("OK".equals(result));
                    return null;
                }
        );
    }

    @Test
    public void luaTest() {
        final String key = "nxLuaKey";
        final String requestId = "requestId1";
        final String seconds = String.valueOf(System.currentTimeMillis());
        final String script = "if (redis.call('exists',KEYS[1]) == 0 or redis.call('get',KEYS[1]) == ARGV[2]) then return redis.call('setex',KEYS[1],ARGV[1],ARGV[2]) else return -1 end";
        redisTemplate.execute((RedisConnection connection) -> {
                    Jedis jedis = (Jedis) connection.getNativeConnection();
                    Object eval = jedis.eval(script, Collections.singletonList(key), Arrays.asList(String.valueOf(seconds), requestId));
                    System.out.println(eval);
                    return null;
                }
        );
    }

}
