package com.seeu.grouper.login.dao;

import com.seeu.core.Mapper;
import com.seeu.grouper.login.model.User;
import com.seeu.grouper.store.model.Store;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface UserMapper extends Mapper<User> {
    @Insert("insert into user(email,password,lastlogin) values(#{email},#{password},#{lastlogin})")
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    int insertSelective2(User var1);
}