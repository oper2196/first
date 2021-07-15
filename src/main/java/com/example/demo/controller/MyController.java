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
import java.util.List;

@RestController
public class MyController {
    Logger logger = LoggerFactory.getLogger(MyController.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Resource
    Dao dao;


    // status 0: 初始状态，可使用; 1：正在使用; 2: 使用过，可再次使用

    @GetMapping("/get/{status}")
    public Object get(@PathVariable(name = "status") int status){
        Account account;
        String updateTime = null;
        if (status == 0) {
            account = dao.getData();
        } else {
            account = dao.getIniAccount();
            if (account != null) {
                updateTime = account.getUpdateTime();
            }
        }

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
        if (status == 0) {
            dao.setUpdateTime(account.getId(), null);
        } else {
            dao.setUpdateTime(account.getId(), updateTime);
        }
        logger.info("update affect {} rows", affectRows);
        return account;
    }

    @GetMapping("/android/get/{status}")
    public Object get_android(@PathVariable(name = "status") int status){
        Account account = null;
        String updateTime = null;
        if (status == 0) {
            account = dao.getData_android();
        } else {
            account = dao.getIniAccount_android();
            updateTime = account.getUpdateTime();
        }

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
        if (status == 0) {
            dao.setUpdateTime_android(account.getId(), null);
        } else {
            dao.setUpdateTime_android(account.getId(), updateTime);
        }
        logger.info("update affect {} rows", affectRows);
        return account;
    }

    @GetMapping(path = "/update")
    public String update(Integer id ,String email ,String password, String detail ,String pid,int status) throws JsonProcessingException {
        System.out.println(id);
        Account account = dao.getAccountByEmail(email);
        if (account == null) {
            dao.saveAccount(email, password, detail, pid, status);
        } else {
            dao.updateAccount(email, password, detail, pid, status);
        }
        dao.updateStatus(id,2);
        Cache.emailCache.remove(id);
        return "更新成功";
    }

    @GetMapping(path = "/hs/update")
    public String update_hs(Integer id ,String email ,String password, String detail ,String pid) throws JsonProcessingException {
        System.out.println(id);
        dao.saveAccount_hs(email, password, detail, pid);
        dao.updateStatus_hs(id, 2);
        Cache.emailCache.remove(id);
        return "更新成功";
    }

    @GetMapping(path = "/android/update")
    public String update_android(Integer id ,String email ,String password, String detail ,String pid) throws JsonProcessingException {
        System.out.println(id);
        Account account = dao.getAccountByEmail_android(email);
        if (account == null) {
            dao.saveAccount_android(email, password, detail, pid);
        } else {
            dao.updateAccount_android(email, password, detail, pid);
        }
        dao.updateStatus_android(id, 2);
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

    @GetMapping("/getList")
    @ResponseBody
    public Object getList(String name, String fullName ) throws JsonProcessingException {
        String _name = '%'+name +'%';
        List<Account> accounts;
        if (fullName != null && fullName.length() != 0) {
            String _name2 = '%'+fullName +'%';
            accounts = dao.getShow2(_name,_name2 );
        } else {
            accounts = dao.getShow(_name);
        }
        return accounts;
    }

    @GetMapping("/getHeros")
    @ResponseBody
    public Object getHeros(String name ) throws JsonProcessingException {
        String _name = '%'+name +'%';
        List<String> accounts = dao.getHeros(_name);
        return accounts;
    }

    @GetMapping("/getEmail")
    @ResponseBody
    public Object getEmail(int id, String user, String password ) throws JsonProcessingException {
        String userId = dao.check(user, password);
        if (userId != null && userId.length() != 0) {
            Account accounts = dao.getEmail(id);
            dao.record(id, user);
            return accounts;
        }
        return null;
    }

}
