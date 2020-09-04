package com.example.demo.controller;

import com.example.demo.cache.Cache;
import com.example.demo.dao.TestDao;
import com.example.demo.entity.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MyController {
    Logger logger = LoggerFactory.getLogger(MyController.class);
    @Resource
    TestDao testDao;

    @GetMapping("/get")
    public Object get(){

        Test test = testDao.getData();

        while(test !=null && Cache.isExist(test.getId())){
            test = testDao.getData();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(test == null)
            return null;

        int affectRows = testDao.updateData(test.getId());
        logger.info("update affect {} rows",affectRows);
        return test;
    }

    @RequestMapping(value = "xxxx/{id}", method = RequestMethod.DELETE)
    public Object update(int id){
        testDao.deleteData(id);
        Cache.emailCache.remove(id);
        return null;
    }

}
