package com.seeu.grouper.store.dao;

import com.seeu.core.Mapper;
import com.seeu.grouper.store.model.Store;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface StoreMapper extends Mapper<Store> {
    @Insert("insert into store(uid,title,price) values(#{uid},#{title},#{price})")
    @Options(useGeneratedKeys = true, keyProperty = "tid", keyColumn = "tid")
    int insertSelective2(Store var1);
}