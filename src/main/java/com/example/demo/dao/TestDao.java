package com.example.demo.dao;

import com.example.demo.entity.Test;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public interface TestDao extends CrudRepository<Test,String>, JpaSpecificationExecutor {

    @Query(value = "select * from test where status=0 LIMIT 1",nativeQuery = true)
    Test getData();

    @Transactional
    @Modifying
    @Query(value = "delete from test where id= ?1 ",nativeQuery = true)
    int deleteData(int id);

    @Transactional
    @Modifying
    @Query(value = "update test set status = 1 where id= ?1 ",nativeQuery = true)
    int updateData(int id);
}
