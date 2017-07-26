package com.seeu.grouper.login.service;

import com.seeu.grouper.login.model.User;
import com.seeu.core.Service;


/**
 * Created by neoxiaoyi on 2017/07/18.
 */
public interface RegistService extends Service<User> {

    public boolean checkEmailAvailable(String email);

    public boolean checkPasswordAvailable(String password);

    public boolean hasRegisted(String email);

    public int insertSelective2(User var1);
}
