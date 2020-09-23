package com.example.demo.controller;

import com.example.demo.cache.Cache;
import com.example.demo.dao.Dao;
import com.example.demo.entity.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class MyController {
    Logger logger = LoggerFactory.getLogger(MyController.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Resource
    Dao dao;

    @GetMapping("/get")
    public Object get(){

        Account account = dao.getData();

        while(account !=null && Cache.isExist(account.getId())){
            account = dao.getData();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(account == null)
            return null;

        int affectRows = dao.updateStatus(account.getId(), 1);
        logger.info("update affect {} rows", affectRows);
        return account;
    }

    @GetMapping(path = "/update")
    public String update(Integer id ,String email ,String password, String detail ,String pid) throws JsonProcessingException {
        System.out.println(id);
        dao.saveAccount(email, password, detail, pid);
        dao.deleteEmail(id);
        Cache.emailCache.remove(id);
        return "更新成功";
    }

    @GetMapping("/ini/{id}")
    public String ini(@PathVariable(name = "id") int id){
        dao.updateStatus(id, 0);
        return "初始化成功";
    }

    @GetMapping("/delete")
    public String ini(String startTime ,String endTime){
        dao.deleteAccount(startTime, endTime);
        return "删除成功";
    }

}
