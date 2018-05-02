import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author LiGuanglong
 * @date 2018/5/2
 */
public class RedisJava {
    public static void main(String[] args) {
//        testConnection();
//        redisStringJava();
//        redisListJava();
//        redisSetJava();
//        redisHashMapJava();
//        redisSortedSetJava();

//        jedisPool();
//        testSameKey();
//        testJedisPool();

        testRemoteConnection();
    }


    public static void testLocationConnection() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");

        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());
    }


    public static void testRemoteConnection(){
        Jedis jedis = new Jedis("47.98.183.125");
        //替换密码
        jedis.auth("**password**");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());
    }


    /**
     * String
     */
    public static void redisStringJava() {
        Jedis jedis = new Jedis("localhost");
        jedis.set("redisString", "testRedisString");
        System.out.println(jedis.get("redisString"));
    }


    /**
     * List
     */
    public static void redisListJava() {
        Jedis jedis = new Jedis("localhost");
        jedis.lpush("redisList", "List1", "List2");
        System.out.println(jedis.lpop("redisList"));
    }


    /**
     * Set
     */
    public static void redisSetJava() {
        Jedis jedis = new Jedis("localhost");
        jedis.sadd("redisSet", "set1", "set2");
        System.out.println(jedis.smembers("redisSet"));
    }

    public static void redisSortedSetJava() {
        Jedis jedis = new Jedis("localhost");
        jedis.zadd("redisSortedSet", 2, "redisSortedSet2");
        jedis.zadd("redisSortedSet", 1, "redisSortedSet1");
        System.out.println(jedis.zrange("redisSortedSet", 0, 2));
    }

    /**
     * HashMap
     */
    public static void redisHashMapJava() {
        Jedis jedis = new Jedis("localhost");
        jedis.hset("redisHashMap", "HashMapKey1", "HashMapValue1");
        System.out.println(jedis.hget("redisHashMap", "HashMapKey1"));
    }


    /**
     * Jedis在多线程环境下不是线程安全的，在多线程下使用同一个Jedis对象会出现并发问题
     * 为了避免每次使用Jedis对象时都需要重新构建
     */
    public synchronized static Jedis getJedis() {
        try {
            //连接池配置
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            //可连接实例的最大数目，默认是8
            jedisPoolConfig.setMaxTotal(10);
            //创建连接池
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");

            if (jedisPool != null) {
                //从连接池中获取jedis
                Jedis jedis = jedisPool.getResource();
                return jedis;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 关闭Jedis,并回收到连接池中
     *
     * @param jedis 方法参数声明为final,表明它是只读的
     */
    public static void closeRedis(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


    public static void testJedisPool() {
        Jedis jedis = getJedis();

        System.out.println(jedis.get("redisString"));
        //保存数据dump.rdb
        jedis.save();
        closeRedis(jedis);

    }


    public static void testSameKey() {
        Jedis jedis = getJedis();
        jedis.set("hello", "String");
        //报错，hello对应的是String，插入hashmap会报错
        jedis.hset("hello", "hash", "map");
        jedis.lpush("hello", "set");

    }


    public static void jedisPool() {
        //连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //可连接实例的最大数目，默认是8
        jedisPoolConfig.setMaxTotal(10);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");
        System.out.println("active:" + jedisPool.getNumActive());
        Jedis jedis = null;
        Jedis jedis1 = null;
        Jedis jedis2 = null;
        try {
            //从连接池中获取
            jedis = jedisPool.getResource();
            System.out.println("active:" + jedisPool.getNumActive());
            jedis.set("jedisPool", "jedisPool");
            System.out.println(jedis.get("jedisPool"));


            jedis1 = jedisPool.getResource();
            System.out.println("active:" + jedisPool.getNumActive());
            jedis.set("jedisPool", "jedisPoolNew");
            System.out.println(jedis.get("jedisPool"));


            jedis2 = jedisPool.getResource();
            System.out.println("active:" + jedisPool.getNumActive());
            jedis.set("jedisPool", "jedisPoolNewNew");
            System.out.println(jedis.get("jedisPool"));


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返回连接池
            if (jedis != null) {
                jedis.close();
                System.out.println("active:" + jedisPool.getNumActive());


                jedis1.close();
                System.out.println("active:" + jedisPool.getNumActive());


                jedis2.close();
                System.out.println("active:" + jedisPool.getNumActive());
            }
        }
        jedisPool.close();
    }

}
