package com.hmdp.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.BooleanUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.yaml.snakeyaml.events.Event;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Program Monkey
 */
public class SimpleRedisLock implements ILock{
    private String name;
    private static final String KEY_PREFIX = "lock:";
    private static final String ID_PREFIX = UUID.randomUUID().toString(true) + "-";
    private StringRedisTemplate stringRedisTemplate;
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }
    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate){
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    public boolean tryLock(long timeoutSec) {
        //获取锁
        String key = KEY_PREFIX + name;
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(key, threadId, timeoutSec, TimeUnit.SECONDS);
        //这里的Boolean和boolean存在自动装箱的空指针风险，万一success是null，那么自动装箱会报错
        return Boolean.TRUE.equals(success);
        //另一种方法
        //return BooleanUtil.isTrue(success);
    }
    @Override
    public void unlock() {
        stringRedisTemplate.execute(UNLOCK_SCRIPT, Collections.singletonList(KEY_PREFIX + name), ID_PREFIX + Thread.currentThread().getId());
    }
    /*
    @Override
    public void unlock() {
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        String value = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
        if(threadId.equals(value)){
            //释放锁完成
            stringRedisTemplate.delete(KEY_PREFIX + name);
        }
    }
    */
}
