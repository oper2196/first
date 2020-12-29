package com.example.demo.action;

import com.example.demo.dao.Dao;
import com.example.demo.entity.Account;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by toutou on 2018/11/3.
 */
@Controller
public class QueryNoticeListAction {
    public static final Logger log = LoggerFactory.getLogger(QueryNoticeListAction.class);
    @Resource
    Dao dao;

    public void excute(){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date lastData = c.getTime();
        String startTime= dateFormat.format(lastData) + " 00:00:00";//前一天
        String endTime = dateFormat.format(lastData) + " 23:59:59";
        List<Account> AccountList = dao.getAccounts(startTime, endTime);
        // 设置要导出的文件的名字
        String fileName = "账号列表-"  + dateFormat.format(date) + ".xls";

        //创建文件夹和文件名
        String locDir = "d:/accounts/";
        File file = new File(locDir);
        if(!file.exists()){
            file.mkdirs();
        }

        String filePath = locDir+fileName;



        // 新增数据行，并且设置单元格数据
        int rowNum = 1;

        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = { "ID", "邮箱", "密码", "备注", "手机编号", "创建时间"};
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (Account item : AccountList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(item.getId());
            row1.createCell(1).setCellValue(item.getEmail());
            row1.createCell(2).setCellValue(item.getPassword());
            row1.createCell(3).setCellValue(item.getDetail());
            row1.createCell(4).setCellValue(item.getPid());
            row1.createCell(5).setCellValue(item.getCreateTime());
            rowNum++;
        }
        FileOutputStream out = null;
        log.info("源文件路径---" + filePath);
        try {
            // 生成Excel的文件名
            out = new FileOutputStream(new File(filePath));
            log.info("导入文件输出流");
            workbook.write(out);
            log.info("刷新文件输出流");
            out.flush();
            log.info("关闭文件输出流");
            out.close();
        } catch (Exception e) {
            log.info("文件生成失败", e);
        }
        log.info("-------生成excel文件成功------");
    }
}