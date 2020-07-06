package com.spring.common.utils;

import java.util.Date;
import java.util.UUID;

/**
 * 描述: Twitter的分布式自增ID雪花算法snowflake (Java版)
 *
 * @author yanpenglei
 * @create 2018-03-13 12:37
 **/
public class SnowFlake {

    private static SnowFlake snowFlake;

    /**
     * 获取实例
     *
     * @return
     */
    public static SnowFlake getInstance() {
        if (snowFlake == null) {
            snowFlake = new SnowFlake(0, 0);
        }
        return snowFlake;
    }

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数  ，数据范围【0，31】
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数  ，数据范围【0，31】

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心 数据范围【0，31】
    private long machineId;     //机器标识 数据范围【0，31】
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    /**
     * @param datacenterId 数据范围【0，31】
     * @param machineId    数据范围【0，31】
     */
    private SnowFlake(int datacenterId, int machineId) {
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    private synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;
        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    /**
     * @return 20200617-185828221-00-00-0001  (29位)
     */
    public String toString29() {
        StringBuffer result = new StringBuffer();
        long uid = nextId();
        long timeStmp = ((uid >>> 22) + START_STMP);
        long datacenterId = uid << 42 >>> 59;
        long machineId = uid << 47 >>> 59;
        long sequence = uid << 52 >>> 52;
        result.append(new Moment().fromDate(new Date(timeStmp)).toFullTime()
                .replaceAll("\\-", "")
                .replaceAll(" ", "-")
                .replaceAll("\\:", "")
                .replaceAll("\\.", "")
        );
        result.append("-" + String.format("%02d", datacenterId));
        result.append("-" + String.format("%02d", machineId));
        result.append("-" + String.format("%04d", sequence));
        return result.toString();
    }

    /**
     * @return 20200617-185828221-0001  (23位)
     */
    public String toString23() {
        StringBuffer result = new StringBuffer();
        long uid = nextId();
        long timeStmp = ((uid >>> 22) + START_STMP);
        long datacenterId = uid << 42 >>> 59;
        long machineId = uid << 47 >>> 59;
        long sequence = uid << 52 >>> 52;
        result.append(new Moment().fromDate(new Date(timeStmp)).toFullTime()
                .replaceAll("\\-", "")
                .replaceAll(" ", "-")
                .replaceAll("\\:", "")
                .replaceAll("\\.", "")
        );
//        result.append("-" + String.format("%02d", datacenterId));
//        result.append("-" + String.format("%02d", machineId));
        result.append("-" + String.format("%04d", sequence));
        return result.toString();
    }

    /**
     * @return 200617 185828221 0001  (19位)
     */
    public String toString19() {
        StringBuffer result = new StringBuffer();
        long uid = nextId();
        long timeStmp = ((uid >>> 22) + START_STMP);
        long datacenterId = uid << 42 >>> 59;
        long machineId = uid << 47 >>> 59;
        long sequence = uid << 52 >>> 52;
        result.append(new Moment().fromDate(new Date(timeStmp)).toFullTime().substring(2)
                .replaceAll("\\-", "")
                .replaceAll(" ", "")
                .replaceAll("\\:", "")
                .replaceAll("\\.", "")
        );
//        result.append("-" + String.format("%02d", datacenterId));
//        result.append("-" + String.format("%02d", machineId));
        result.append("" + String.format("%04d", sequence));
        return result.toString();
    }

    /**
     * 数据表ID
     *
     * @return 200617 185828221 0001  (19位)
     */
    public long toLong19() {
        StringBuffer result = new StringBuffer();
        long uid = nextId();
        long timeStmp = ((uid >>> 22) + START_STMP);
        long datacenterId = uid << 42 >>> 59;
        long machineId = uid << 47 >>> 59;
        long sequence = uid << 52 >>> 52;
        result.append(new Moment().fromDate(new Date(timeStmp)).toFullTime().substring(2)
                .replaceAll("\\-", "")
                .replaceAll(" ", "")
                .replaceAll("\\:", "")
                .replaceAll("\\.", "")
        );
//        result.append("-" + String.format("%02d", datacenterId));
//        result.append("-" + String.format("%02d", machineId));
        result.append("" + String.format("%04d", sequence));
        return Long.parseLong(result.toString());
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        System.err.println(System.currentTimeMillis());
        System.err.println(new Moment().toFullTime());
        System.out.println("数据表ID：" + SnowFlake.getInstance().toLong19());
        System.out.println("UUID：" + UUID.randomUUID().toString().replaceAll("-","")+" 长度："+UUID.randomUUID().toString().length());

    }
}