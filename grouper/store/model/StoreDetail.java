package com.seeu.grouper.store.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "store_detail")
public class StoreDetail {
    @Id
    private Integer tid;

    private Integer uid;

    private String note;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 详情信息
     */
    @Column(name = "html_note")
    private String htmlNote;

    /**
     * @return tid
     */
    public Integer getTid() {
        return tid;
    }

    /**
     * @param tid
     */
    public void setTid(Integer tid) {
        this.tid = tid;
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
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取详情信息
     *
     * @return html_note - 详情信息
     */
    public String getHtmlNote() {
        return htmlNote;
    }

    /**
     * 设置详情信息
     *
     * @param htmlNote 详情信息
     */
    public void setHtmlNote(String htmlNote) {
        this.htmlNote = htmlNote;
    }
}