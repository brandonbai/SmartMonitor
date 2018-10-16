package com.github.brandonbai.smartmonitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void stringTest() {
        redisTemplate.opsForValue().set("abc", "def");
        Object o = redisTemplate.opsForValue().get("abc");
        System.out.println(o);
    }

    @Test
    public void hashTest() {
        redisTemplate.opsForHash().put("obj", "name", "xiaobai");
        redisTemplate.opsForHash().put("obj", "age", "24");
        Object o = redisTemplate.opsForHash().get("obj", "name");
        System.out.println(o);
    }

    @Test
    public void setTest() {
        redisTemplate.opsForSet().add("set", "1", "2", "3", "4", "5");
        Object o = redisTemplate.opsForSet().members("set");
        System.out.println(o);
    }

    @Test
    public void zSetTest() {
        redisTemplate.opsForZSet().add("zset", "1", 1);
        redisTemplate.opsForZSet().add("zset", "2", 2);
        Object o1 = redisTemplate.opsForZSet().range("zset", 0, 1);
        Object o2 = redisTemplate.opsForZSet().rangeByScore("zset", 1, 2);
        System.out.println(o1);
        System.out.println(o2);
    }

    @Test
    public void listTest() {
        redisTemplate.opsForList().rightPush("list", "a");
        redisTemplate.opsForList().rightPush("list", "b");
        Object o = redisTemplate.opsForList().leftPop("list");
        System.out.println(o);
    }

    @Test
    public void geoTest() {
        redisTemplate.opsForGeo().geoAdd("company", new RedisGeoCommands.GeoLocation("home", new Point(116.30951732397079468, 40.05753808116615033)));
        redisTemplate.opsForGeo().geoAdd("company", new RedisGeoCommands.GeoLocation("work", new Point(116.47473067045211792, 39.89852234923503005)));
        Distance dist = redisTemplate.opsForGeo().geoDist("company", "home", "work", Metrics.KILOMETERS);
        System.out.println(dist.getValue());
    }

    @Test
    public void hyperLogLogTest() {
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForHyperLogLog().add("hyperLogLog", "user" + i);
        }
        long total = redisTemplate.opsForHyperLogLog().size("hyperLogLog");
        System.out.printf("%d %d\n", 100000, total);
    }

    @Test
    public void bitMapTest() {
        redisTemplate.opsForValue().setBit("bitmap", 1, true);
        redisTemplate.opsForValue().setBit("bitmap", 2, true);
        redisTemplate.opsForValue().setBit("bitmap", 4, true);
        Object o = redisTemplate.opsForValue().get("bitmap");
        System.out.println(o);
    }

}
