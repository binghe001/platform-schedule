package com.courage.platform.schedule.server.service;

import com.courage.platform.schedule.dao.ScheduleJobInfoDao;
import com.courage.platform.schedule.dao.domain.ScheduleJobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调度服务任务基础服务
 * Created by zhangyong on 2019/11/3.
 */
@Service
public class ScheduleJobInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleJobInfoService.class);

    private static ConcurrentHashMap<Long, ScheduleJobInfo> JOB_INFO_CACHE = new ConcurrentHashMap<>();

    @Autowired
    private ScheduleJobInfoDao scheduleJobInfoDao;

    //1分钟加载一次任务信息
    @Scheduled(initialDelay = 0, fixedRate = 60000)
    @PostConstruct
    public void loadCache() {
        long start = System.currentTimeMillis();
        logger.info("开始加载任务基础信息数据");
        try {
            List<ScheduleJobInfo> appinfoList = scheduleJobInfoDao.findAll();
            for (ScheduleJobInfo scheduleJobInfo : appinfoList) {
                JOB_INFO_CACHE.put(scheduleJobInfo.getId(), scheduleJobInfo);
            }
        } catch (Exception e) {
            logger.error("loadCache error: ", e);
        }
        logger.info("结束加载任务基础信息数据,耗时:" + (System.currentTimeMillis() - start) + "ms");
    }

    public ScheduleJobInfo getById(Long id) {
        return JOB_INFO_CACHE.get(id);
    }

}
