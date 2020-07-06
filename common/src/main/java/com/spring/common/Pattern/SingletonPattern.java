package com.spring.common.Pattern;

public class SingletonPattern {
    private volatile static SingletonPattern singletonPattern;

    /**
     * 单例模式的时候-构造函数为private
     */
    private SingletonPattern() {
    }

    /**
     * 双检锁/双重校验锁/线程安全
     *
     * @return
     */
    public static SingletonPattern getInstance() {
        if (singletonPattern == null) {
            synchronized (SingletonPattern.class) {
                if (singletonPattern == null) {
                    singletonPattern = new SingletonPattern();
                }
            }
        }
        return singletonPattern;
    }
}
