package com.seeu.grouper.user.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_basic")
public class UserBasic {
    @Id
    private Integer uid;

    private String nickname;

    /**
     * 0 未知
1 男
2 女
     */
    private Integer gender;

    @Column(name = "school_id")
    private String schoolId;

    @Column(name = "real_name")
    private String realName;

    private String qq;

    private String wechat;

    private String weibo;

    private String email;

    private String phone;

    private String icon;

    private String persign;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取0 未知
1 男
2 女
     *
     * @return gender - 0 未知
1 男
2 女
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置0 未知
1 男
2 女
     *
     * @param gender 0 未知
1 男
2 女
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return school_id
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * @return real_name
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * @return wechat
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * @param wechat
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * @return weibo
     */
    public String getWeibo() {
        return weibo;
    }

    /**
     * @param weibo
     */
    public void setWeibo(String weibo) {
        this.weibo = weibo;
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
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return persign
     */
    public String getPersign() {
        return persign;
    }

    /**
     * @param persign
     */
    public void setPersign(String persign) {
        this.persign = persign;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}