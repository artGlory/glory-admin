package com.spring.webadmin.job;

import com.spring.common.cacheDao.LogOperateCacheDao;
import com.spring.common.po.LogOperate;
import com.spring.common.utils.PrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
public class LogOperateSaveJob {
    @Autowired
    private LogOperateCacheDao logOperateCacheDao;
    /**
     * 日志队列
     */
    private static final ConcurrentLinkedQueue<LogOperate> concurrentLinkedQueue
            = new ConcurrentLinkedQueue<LogOperate>();

    boolean isRun = false;

    @Scheduled(fixedDelay = 1000 * 60 * 1, initialDelay = 1000 * 60 * 1)
    @Async("logSaveExecutor")
    public void work() {
        if (isRun) return;
        isRun = true;
        try {
            int size = concurrentLinkedQueue.size();
            List<LogOperate> logOperateList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                logOperateList.add(concurrentLinkedQueue.poll());
            }
            for (LogOperate logOperate : logOperateList) {
                int result = logOperateCacheDao.insert(logOperate);
                if (result != 1) {
                    log.error("操作日志插入失败" + ToStringBuilder.reflectionToString(logOperate));
                }
            }

        } catch (Exception e) {
            log.info(PrintUtil.getStackTrace(e));
        } finally {
            isRun = false;
        }
    }

    public static boolean addOperateLog(LogOperate logOperate) {
        concurrentLinkedQueue.offer(logOperate);
        return true;
    }

    public static boolean addOperateLog(List<LogOperate> logOperateList) {
        for (LogOperate logOperate : logOperateList) {
            addOperateLog(logOperate);
        }
        return true;
    }

}
