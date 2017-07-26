package com.seeu.grouper.login.service;

import com.seeu.grouper.login.model.LoginLog;
import com.seeu.core.Service;
import com.seeu.grouper.login.util.LoginLogException;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by neoxiaoyi on 2017/07/18.
 */
public interface LoginLogService extends Service<LoginLog> {
    public void log(HttpServletRequest request, Integer uid) throws LoginLogException;

}
