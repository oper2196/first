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


    @Transactional
    @Modifying
    @Query(value = "update accounts set user = ?2, selled=1 where id= ?1 ",nativeQuery = true)
    int record(int id, String user);

    @Query(value = "select id,detail,price,pricesell,password ,email, '' as createtime,'' as  updatetime , status, '' as user, '' as pid  from accounts where id = ?1 and selled = 0",nativeQuery = true)
    Account getEmail(int id);

    @Query(value = "select user from users where user = ?1 and password =?2 ",nativeQuery = true)
    String check(String user, String password);

    @Query(value = "select detail from accounts where detail LIKE :name  and selled = 0 order by price ",nativeQuery = true)
    List<String> getHeros(String name);

    @Query(value = "select id,detail,price,pricesell,password ,email, '' as createtime,'' as  updatetime , status, '' as user, '' as pid  from accounts where detail LIKE ?1 and  detail LIKE ?2  and selled = 0 and status = ?3 order by price desc ",nativeQuery = true)
    List<Account> getShow2(String name, String name2, int status);

    @Query(value = "select id,detail,price,pricesell,'' as password ,'' as email, '' as createtime,'' as  updatetime , status, '' as user, '' as pid  from accounts where detail LIKE ?1 and selled = 0 and status =?2 order by price desc",nativeQuery = true)
    List<Account> getShow(String name, int status);

    @Query(value = "select id,detail,price,pricesell,password ,email, '' as createtime,'' as  updatetime , status, '' as user, '' as pid  from accounts where detail LIKE ?1 and  detail LIKE ?2  and selled = 0  order by price desc",nativeQuery = true)
    List<Account> getShow2_all(String name, String name2);

    @Query(value = "select id,detail,price,pricesell,'' as password ,'' as email, '' as createtime,'' as  updatetime , '' as status, '' as user, '' as pid  from accounts where detail LIKE ?1 and selled = 0  order by price desc",nativeQuery = true)
    List<Account> getShow_all(String name);

    @Query(value = "select * from accounts where email = ?1",nativeQuery = true)
    Account getAccountByEmail(String email);

    @Query(value = "select * from accounts_android where email = ?1",nativeQuery = true)
    Account getAccountByEmail_android(String email);

    @Query(value = "select *,'' as price, '' as pricesell, '' as user from emails where status = 2 and  TIMESTAMPDIFF(SECOND ,CONCAT( CURDATE(),' 00:00:00'),updatetime) <= 0 LIMIT 1",nativeQuery = true)
    Account getIniAccount();

    @Query(value = "select * from emails_android where status = 2 and  TIMESTAMPDIFF(SECOND ,CONCAT( CURDATE(),' 00:00:00'),updatetime) <= 0 LIMIT 1",nativeQuery = true)
    Account getIniAccount_android();

    @Query(value = "select *,'' as price, '' as pricesell, '' as user  from emails where status = 0 LIMIT 1",nativeQuery = true)
    Account getData();

    @Query(value = "select * from emails_android where status = 0 LIMIT 1",nativeQuery = true)
    Account getData_android();

    @Query(value = "select * from accounts where createtime >= ?1 and createtime < ?2",nativeQuery = true)
    List<Account> getAccounts(String startTime, String endTime);

    @Transactional
    @Modifying
    @Query(value = "delete from emails where id= ?1 ",nativeQuery = true)
    int deleteEmail(int id);

    @Transactional
    @Modifying
    @Query(value = "delete from emails_android where id= ?1 ",nativeQuery = true)
    int deleteEmail_android(int id);

    @Transactional
    @Modifying
    @Query(value = "delete from accounts where createtime >= ?1 and createtime < ?2",nativeQuery = true)
    int deleteAccount(String startTime, String endTime);

    @Transactional
    @Modifying
    @Query(value = "delete from accounts_hs where createtime >= ?1 and createtime < ?2",nativeQuery = true)
    int deleteAccount_hs(String startTime, String endTime);

    @Transactional
    @Modifying
    @Query(value = "update emails set status = ?2 where id= ?1 ",nativeQuery = true)
    int updateStatus(int id, int status);

    @Transactional
    @Modifying
    @Query(value = "update emails set status = ?2 where email= ?1 ",nativeQuery = true)
    int updateStatusByEmail(String email, int status);

    @Transactional
    @Modifying
    @Query(value = "update emails_android set status = ?2 where id= ?1 ",nativeQuery = true)
    int updateStatus_hs(int id, int status);

    @Transactional
    @Modifying
    @Query(value = "update emails_android set status = ?2 where id= ?1 ",nativeQuery = true)
    int updateStatus_android(int id, int status);

    @Transactional
    @Modifying
    @Query(value = "update emails set detail = ?2, status = 2 where id= ?1 ",nativeQuery = true)
    int updateData(int id, String detail);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO accounts (email, password, detail, pid,status) VALUES (?1, ?2, ?3, ?4,?5)",nativeQuery = true)
    int saveAccount(String email, String password, String detail, String pid, int status);

    @Transactional
    @Modifying
    @Query(value = "UPDATE accounts set price=null ,pricesell=null ,email = ?1, password = ?2, detail = ?3, pid = ?4, status=?5 where email = ?1",nativeQuery = true)
    int updateAccount(String email, String password, String detail, String pid, int status);

    @Transactional
    @Modifying
    @Query(value = "update accounts_android set email = ?1, password = ?2, detail = ?3, pid = 4) where email = ?1",nativeQuery = true)
    int updateAccount_android(String email, String password, String detail, String pid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO accounts_hs (email, password, detail, pid) VALUES (?1, ?2, ?3, ?4)",nativeQuery = true)
    int saveAccount_hs(String email, String password, String detail, String pid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO accounts_android (email, password, detail, pid) VALUES (?1, ?2, ?3, ?4)",nativeQuery = true)
    int saveAccount_android(String email, String password, String detail, String pid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO emails (email, password) select ?1, ?2 from dual where not exists (select email from emails where email = ?1)",nativeQuery = true)
    int insertEmail(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO emails_android (email, password) select ?1, ?2 from dual where not exists (select email from emails_android where email = ?1)",nativeQuery = true)
    int insertEmail_android(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE emails SET updatetime = ?2 where id= ?1 ",nativeQuery = true)
    int setUpdateTime(int id, String updatetime);

    @Transactional
    @Modifying
    @Query(value = "UPDATE emails_android SET updatetime = ?2 where id= ?1 ",nativeQuery = true)
    int setUpdateTime_android(int id, String updatetime);

    @Query(value = "select id,detail,price,pricesell,'' as password ,email, '' as createtime, updatetime , status, user, '' as pid  from accounts where user = ?1 and selled = 1 order by updatetime DESC",nativeQuery = true)
    List<Account> getHistory(String user);
}
