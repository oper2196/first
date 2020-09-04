package com.example.demo.dao;

import com.example.demo.entity.Test;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TestDao extends CrudRepository<Test,String>, JpaSpecificationExecutor {

    @Query(value = "select  * from test where id= ?1 ",nativeQuery = true)
    Test getLastedOne(int id);

}
