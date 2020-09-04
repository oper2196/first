package com.example.demo.controller;

import com.example.demo.dao.TestDao;
import com.example.demo.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MyController {

    @Resource
    TestDao testDao;

    @GetMapping("/test")
    public Object test(){

        Test list = testDao.getLastedOne(1);

        return list;
    }

}
