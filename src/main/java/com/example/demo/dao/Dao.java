package com.example.demo.dao;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public interface Dao extends CrudRepository<Account,String>, JpaSpecificationExecutor {

    @Query(value = "select * from test where status=0 LIMIT 1",nativeQuery = true)
    Account getData();

    @Transactional
    @Modifying
    @Query(value = "delete from test where id= ?1 ",nativeQuery = true)
    int deleteData(int id);

    @Transactional
    @Modifying
    @Query(value = "update test set status = ?2 where id= ?1 ",nativeQuery = true)
    int updateStatus(int id, int status);

    @Transactional
    @Modifying
    @Query(value = "update test set detail = ?2 where id= ?1 ",nativeQuery = true)
    int updateData(int id, String detail);
}
