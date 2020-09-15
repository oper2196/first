package com.example.demo.controller;

import com.example.demo.dao.Dao;
import com.example.demo.entity.Account;
import com.example.demo.util.ExcelImportUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public void Export(@RequestParam String startTime , @RequestParam String endTime ,HttpServletResponse response) throws IOException {
        System.out.println(startTime);
        System.out.println(endTime);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<Account> AccountList = dao.getAccounts(startTime, endTime);

        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//设置当前时间的格
        // 设置要导出的文件的名字
        String fileName = "账号列表-"  + dateFormat.format(date) + ".xls";



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

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 导入账号信息
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()){
            return "文件为空！";
        }
        InputStream is = null;
        try
        {
            is = file.getInputStream();
            //获取文件名
            String fileName = file.getOriginalFilename();

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            Sheet sheetAt = null;
            //根据文件名判断文件是2003版本还是2007版本
            if(ExcelImportUtils.isExcel2007(fileName)){
                wb = new XSSFWorkbook(is);
                sheetAt = wb.getSheetAt(0);
            }else{
                wb = new HSSFWorkbook(is);
                sheetAt = wb.getSheetAt(0);
            }

            List<Account> accountList = new ArrayList<Account>();

            for (Row row : sheetAt) {
                int rowNum = row.getRowNum();

                String info = row.getCell(0).getStringCellValue();//账号和密码信息


                //数据封装 ，存到数据库
                Account account = new Account();
                String email = info.split("-")[0];
                String password = info.split("-")[1];
                account.setEmail(email);
                account.setPassword(password);

                accountList.add(account);
            }

            //保存数据到DB
        if(accountList.size()>0)
            for (Account account : accountList) {
                dao.insertEmail(account.getEmail(), account.getPassword());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return e.getMessage();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "文件流未关闭";
                }
            }
        }

        return "上传成功";
    }
}