package com.seeu.grouper.store.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Store {
    @Id
    private Integer tid;

    private Integer uid;

    /**
     * 商品状态
0 未上架
1 已上架
2 完成交易
-1 删除
     */
    private Integer status;

    private String title;

    private String location;

    /**
     * 浏览次数
     */
    @Column(name = "view_time")
    private Integer viewTime;

    /**
     * 收藏次数
     */
    @Column(name = "like_time")
    private Integer likeTime;

    /**
     * 最大参与人数
     */
    @Column(name = "max_join")
    private Integer maxJoin;

    /**
     * 当前参与人数
     */
    @Column(name = "current_join")
    private Integer currentJoin;

    @Column(name = "start_join")
    private Integer startJoin;

    private BigDecimal lat;

    private BigDecimal lon;

    /**
     * 预览图片
     */
    private String img;

    private BigDecimal price;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "pub_time_start")
    private Date pubTimeStart;

    @Column(name = "pub_time_arrival")
    private Date pubTimeArrival;

    @Column(name = "pub_loca_start")
    private String pubLocaStart;

    @Column(name = "pub_loca_arrival")
    private String pubLocaArrival;

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
     * 获取商品状态
0 未上架
1 已上架
2 完成交易
-1 删除
     *
     * @return status - 商品状态
0 未上架
1 已上架
2 完成交易
-1 删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置商品状态
0 未上架
1 已上架
2 完成交易
-1 删除
     *
     * @param status 商品状态
0 未上架
1 已上架
2 完成交易
-1 删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获取浏览次数
     *
     * @return view_time - 浏览次数
     */
    public Integer getViewTime() {
        return viewTime;
    }

    /**
     * 设置浏览次数
     *
     * @param viewTime 浏览次数
     */
    public void setViewTime(Integer viewTime) {
        this.viewTime = viewTime;
    }

    /**
     * 获取收藏次数
     *
     * @return like_time - 收藏次数
     */
    public Integer getLikeTime() {
        return likeTime;
    }

    /**
     * 设置收藏次数
     *
     * @param likeTime 收藏次数
     */
    public void setLikeTime(Integer likeTime) {
        this.likeTime = likeTime;
    }

    /**
     * 获取最大参与人数
     *
     * @return max_join - 最大参与人数
     */
    public Integer getMaxJoin() {
        return maxJoin;
    }

    /**
     * 设置最大参与人数
     *
     * @param maxJoin 最大参与人数
     */
    public void setMaxJoin(Integer maxJoin) {
        this.maxJoin = maxJoin;
    }

    /**
     * 获取当前参与人数
     *
     * @return current_join - 当前参与人数
     */
    public Integer getCurrentJoin() {
        return currentJoin;
    }

    /**
     * 设置当前参与人数
     *
     * @param currentJoin 当前参与人数
     */
    public void setCurrentJoin(Integer currentJoin) {
        this.currentJoin = currentJoin;
    }

    /**
     * @return start_join
     */
    public Integer getStartJoin() {
        return startJoin;
    }

    /**
     * @param startJoin
     */
    public void setStartJoin(Integer startJoin) {
        this.startJoin = startJoin;
    }

    /**
     * @return lat
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * @param lat
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    /**
     * @return lon
     */
    public BigDecimal getLon() {
        return lon;
    }

    /**
     * @param lon
     */
    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    /**
     * 获取预览图片
     *
     * @return img - 预览图片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置预览图片
     *
     * @param img 预览图片
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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

    /**
     * @return pub_time_start
     */
    public Date getPubTimeStart() {
        return pubTimeStart;
    }

    /**
     * @param pubTimeStart
     */
    public void setPubTimeStart(Date pubTimeStart) {
        this.pubTimeStart = pubTimeStart;
    }

    /**
     * @return pub_time_arrival
     */
    public Date getPubTimeArrival() {
        return pubTimeArrival;
    }

    /**
     * @param pubTimeArrival
     */
    public void setPubTimeArrival(Date pubTimeArrival) {
        this.pubTimeArrival = pubTimeArrival;
    }

    /**
     * @return pub_loca_start
     */
    public String getPubLocaStart() {
        return pubLocaStart;
    }

    /**
     * @param pubLocaStart
     */
    public void setPubLocaStart(String pubLocaStart) {
        this.pubLocaStart = pubLocaStart;
    }

    /**
     * @return pub_loca_arrival
     */
    public String getPubLocaArrival() {
        return pubLocaArrival;
    }

    /**
     * @param pubLocaArrival
     */
    public void setPubLocaArrival(String pubLocaArrival) {
        this.pubLocaArrival = pubLocaArrival;
    }
}