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

    @PostMapping(path = "/update")
    public Object update(@RequestBody Account account) throws JsonProcessingException {
        String json = mapper.writeValueAsString(account);
        logger.info("update {}", json);
        dao.updateData(account.getId(), account.getDetail());
        Cache.emailCache.remove(account.getId());
        return null;
    }

    @GetMapping("/ini/{id}")
    public Object ini(@PathVariable(name = "id") int id){
        dao.updateStatus(id, 0);
        return null;
    }

}
