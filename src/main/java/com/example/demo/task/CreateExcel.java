package com.example.demo.task;

import com.example.demo.action.QueryNoticeListAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class CreateExcel {
    private Logger logger = LoggerFactory.getLogger(CreateExcel.class);

    @Resource
    QueryNoticeListAction queryNoticeListAction;

    @Scheduled(cron = "0 0 0 * * ?") //每天0点生成一次excel
    public void createExcelAtMr(){
        logger.info("---开始生成Excel---");
        queryNoticeListAction.excute();
        logger.info("---完成生成Excel---");
    }
}