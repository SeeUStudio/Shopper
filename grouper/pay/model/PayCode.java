package com.seeu.grouper.pay.model;

import javax.persistence.*;

@Table(name = "pay_code")
public class PayCode {
    @Id
    private Integer uid;

    /**
     * http://seeu.studio:12001/xxx.png 
完整的HTTP链接
     */
    private String wechat;

    private String alipay;

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
     * 获取http://seeu.studio:12001/xxx.png 
完整的HTTP链接
     *
     * @return wechat - http://seeu.studio:12001/xxx.png 
完整的HTTP链接
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * 设置http://seeu.studio:12001/xxx.png 
完整的HTTP链接
     *
     * @param wechat http://seeu.studio:12001/xxx.png 
完整的HTTP链接
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * @return alipay
     */
    public String getAlipay() {
        return alipay;
    }

    /**
     * @param alipay
     */
    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }
}