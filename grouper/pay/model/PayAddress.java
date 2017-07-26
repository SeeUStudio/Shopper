package com.seeu.grouper.pay.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "pay_address")
public class PayAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uid;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 经度
     */
    private BigDecimal lat;

    /**
     * 纬度
     */
    private BigDecimal lon;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 收件人
     */
    private String name;

    /**
     * 0 初始值
1 默认地址
     */
    private Integer label;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取城市
     *
     * @return city - 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取区
     *
     * @return district - 区
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置区
     *
     * @param district 区
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获取经度
     *
     * @return lat - 经度
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * 设置经度
     *
     * @param lat 经度
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    /**
     * 获取纬度
     *
     * @return lon - 纬度
     */
    public BigDecimal getLon() {
        return lon;
    }

    /**
     * 设置纬度
     *
     * @param lon 纬度
     */
    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    /**
     * 获取详细地址
     *
     * @return detail - 详细地址
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置详细地址
     *
     * @param detail 详细地址
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取收件人
     *
     * @return name - 收件人
     */
    public String getName() {
        return name;
    }

    /**
     * 设置收件人
     *
     * @param name 收件人
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取0 初始值
1 默认地址
     *
     * @return label - 0 初始值
1 默认地址
     */
    public Integer getLabel() {
        return label;
    }

    /**
     * 设置0 初始值
1 默认地址
     *
     * @param label 0 初始值
1 默认地址
     */
    public void setLabel(Integer label) {
        this.label = label;
    }
}