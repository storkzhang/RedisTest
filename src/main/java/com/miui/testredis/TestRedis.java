package com.miui.testredis;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
/**
 * Created by storkzhang on 17-5-4.
 */
public class TestRedis {
    private HashSet<HostAndPort> mClusterNodes;
    private JedisPoolConfig mConfig;
    private JedisCluster mJedisCluster;

    private void genClusterNode() {
        mClusterNodes = new HashSet<HostAndPort>();
        mClusterNodes.add(new HostAndPort("127.0.0.1", 7001));
        mClusterNodes.add(new HostAndPort("127.0.0.1", 7002));
        mClusterNodes.add(new HostAndPort("127.0.0.1", 7003));
        mClusterNodes.add(new HostAndPort("127.0.0.1", 7004));
        mClusterNodes.add(new HostAndPort("127.0.0.1", 7005));
        mClusterNodes.add(new HostAndPort("127.0.0.1", 7006));
    }

    private void genJedisConfig() {
        mConfig = new JedisPoolConfig();
        mConfig.setMaxTotal(1000);
        mConfig.setMaxIdle(100);
        mConfig.setTestOnBorrow(true);
    }

    public void clusterInit() {
        genClusterNode();
        genJedisConfig();
        mJedisCluster = new JedisCluster(mClusterNodes, 5000, mConfig);
    }

    private void setKey(String key, String value) {
        mJedisCluster.set(key, value);
    }

    private String getKey(String key) {
        return mJedisCluster.get(key);
    }


    public static void main(String args[]) {
        String value;
        TestRedis testRedis = new TestRedis();
        testRedis.genJedisConfig();
        testRedis.genClusterNode();
        testRedis.clusterInit();
        testRedis.setKey("mykey", "myvalue");
        value = testRedis.getKey("mykey");
        System.out.println(value);

    }
}
