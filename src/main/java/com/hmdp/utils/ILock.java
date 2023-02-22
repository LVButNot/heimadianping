package com.hmdp.utils;

/**
 * @author Program Monkey
 */
public interface ILock {
    boolean tryLock(long timeoutSec);
    void unlock();
}
