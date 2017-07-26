package com.seeu.grouper.user.model;

import javax.persistence.*;

@Table(name = "user_tag")
public class UserTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uid;

    private String tag;

    /**
     * #80FFFFEE
去掉#，共 8 位
     */
    private String color;

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
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取#80FFFFEE
去掉#，共 8 位
     *
     * @return color - #80FFFFEE
去掉#，共 8 位
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置#80FFFFEE
去掉#，共 8 位
     *
     * @param color #80FFFFEE
去掉#，共 8 位
     */
    public void setColor(String color) {
        this.color = color;
    }
}