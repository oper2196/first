package com.example.demo.dao;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface Dao extends CrudRepository<Account,String>, JpaSpecificationExecutor {

    @Query(value = "select * from emails where status = 0 and HOUR( timediff( now(), updatetime) ) >= 24 LIMIT 1",nativeQuery = true)
    Account getData();

    @Query(value = "select * from accounts",nativeQuery = true)
    List<Account> getAccounts();

    @Transactional
    @Modifying
    @Query(value = "delete from emails where id= ?1 ",nativeQuery = true)
    int deleteData(int id);

    @Transactional
    @Modifying
    @Query(value = "update emails set status = ?2 where id= ?1 ",nativeQuery = true)
    int updateStatus(int id, int status);

    @Transactional
    @Modifying
    @Query(value = "update emails set detail = ?2, status = 2 where id= ?1 ",nativeQuery = true)
    int updateData(int id, String detail);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO accounts (email, password, detail) VALUES (?1, ?2, ?3)",nativeQuery = true)
    int saveAccount(String email, String password, String detail);
}
