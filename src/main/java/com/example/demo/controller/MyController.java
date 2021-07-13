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

    @GetMapping("/android/get")
    public Object get_android(){

        Account account = dao.getData_android();

        while(account !=null && Cache.isExist(account.getId())){
            account = dao.getData_android();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(account == null)
            return null;

        int affectRows = dao.updateStatus_android(account.getId(), 1);
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

    @GetMapping(path = "/hs/update")
    public String update_hs(Integer id ,String email ,String password, String detail ,String pid) throws JsonProcessingException {
        System.out.println(id);
        dao.saveAccount_hs(email, password, detail, pid);
        dao.deleteEmail(id);
        Cache.emailCache.remove(id);
        return "更新成功";
    }

    @GetMapping(path = "/android/update")
    public String update_android(Integer id ,String email ,String password, String detail ,String pid) throws JsonProcessingException {
        System.out.println(id);
        dao.saveAccount_android(email, password, detail, pid);
        dao.deleteEmail_android(id);
        Cache.emailCache.remove(id);
        return "更新成功";
    }

    @GetMapping("/ini/{id}")
    public String ini(@PathVariable(name = "id") int id){
        dao.updateStatus(id, 0);
        return "初始化成功";
    }

    @GetMapping("/android/ini/{id}")
    public String ini_android(@PathVariable(name = "id") int id){
        dao.updateStatus_android(id, 0);
        return "初始化成功";
    }

    @GetMapping("/delete")
    public String delete(String startTime ,String endTime){
        dao.deleteAccount(startTime, endTime);
        return "删除成功";
    }

    @GetMapping("/hs/delete")
    public String delete_hs(String startTime ,String endTime){
        dao.deleteAccount_hs(startTime, endTime);
        return "删除成功";
    }

}
