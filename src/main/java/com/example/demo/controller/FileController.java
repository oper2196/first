package com.example.demo.controller;

import com.example.demo.dao.Dao;
import com.example.demo.entity.Account;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by toutou on 2018/11/3.
 */
@Controller
public class FileController {
    @Resource
    Dao dao;

    @RequestMapping(value = "export")
    public void Export(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<Account> AccountList = dao.getAccounts();

        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//设置当前时间的格
        // 设置要导出的文件的名字
        String fileName = "账号列表-"  + dateFormat.format(date) + ".xls";



        // 新增数据行，并且设置单元格数据
        int rowNum = 1;

        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = { "ID", "邮箱", "密码", "备注", "创建时间"};
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
            row1.createCell(4).setCellValue(item.getCreateTime());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}