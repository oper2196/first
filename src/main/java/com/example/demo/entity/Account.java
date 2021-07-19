package com.example.demo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "detail")
    private String detail;

    @Column(name = "createtime")
    private String createTime;

    @Column(name = "updatetime")
    private String updatetime;

    @Column(name = "pid")
    private String pid;

    @Column(name = "user")
    private String user;

    @Column(name = "price")
    private String price;

    @Column(name = "pricesell")
    private String pricesell;

    @Column(name = "status")
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUpdateTime() {
        return updatetime;
    }

    public void setUpdateTime(String updatetime) { this.updatetime = updatetime;}

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUser() {return user;}

    public void setUser(String user) {this.user = user; }
    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price; }

    public String getPriceSell() {return pricesell;}

    public void setPriceSell(String pricesell) {this.pricesell = pricesell; }

    public int getStatus() {return status;}

    public void setStatus(int status) {this.status = status; }
}
