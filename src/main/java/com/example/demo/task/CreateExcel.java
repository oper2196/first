package com.example.demo.task;

import com.example.demo.action.QueryNoticeListAction;
import com.example.demo.sendemail.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CreateExcel {
    private Logger logger = LoggerFactory.getLogger(CreateExcel.class);

    @Resource
    QueryNoticeListAction queryNoticeListAction;

    @Resource
    private IMailService mailService;

    @Scheduled(cron = "0 0 0 * * ?") //每天0点生成一次excel @Scheduled(fixedRate = 10000)
    public void createExcelAtMr(){
        logger.info("---开始生成Excel---");
        queryNoticeListAction.excute();
        logger.info("---完成生成Excel---");
        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格
        String filePath = "D:\\accounts\\账号列表-"  + dateFormat.format(date) + ".xlsx";
        mailService.sendAttachmentsMail("138122543@qq.com","账号列表-" + dateFormat.format(date),"内容：老婆，我爱你", filePath);
    }
}