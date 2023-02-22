package com.hmdp.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Program Monkey
 */
@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
