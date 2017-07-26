package com.seeu.grouper.login.model;

import java.util.Date;
import javax.persistence.*;

public class User {
    @Id
    private Integer uid;

    private String email;

    private String password;

    private Date lastlogin;

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return lastlogin
     */
    public Date getLastlogin() {
        return lastlogin;
    }

    /**
     * @param lastlogin
     */
    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }
}