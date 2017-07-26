package com.seeu.grouper.user.service;
import com.seeu.grouper.login.model.User;
import com.seeu.grouper.user.model.UserBasic;
import com.seeu.core.Service;


/**
 * Created by neoxiaoyi on 2017/07/19.
 */
public interface UserBasicService extends Service<UserBasic> {

    public void insertNewUser(User user);
}
