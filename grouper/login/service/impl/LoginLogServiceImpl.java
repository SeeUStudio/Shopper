package com.seeu.grouper.login.service.impl;

import com.seeu.grouper.login.dao.LoginLogMapper;
import com.seeu.grouper.login.dao.UserMapper;
import com.seeu.grouper.login.model.LoginLog;
import com.seeu.grouper.login.model.User;
import com.seeu.grouper.login.service.LoginLogService;
import com.seeu.core.AbstractService;
import com.seeu.grouper.login.util.LoginLogException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * Created by neoxiaoyi on 2017/07/18.
 */
@Service
@Transactional
public class LoginLogServiceImpl extends AbstractService<LoginLog> implements LoginLogService {
    @Resource
    private LoginLogMapper loginLogMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 登录日志不应该影响业务正常进行，外界需自行捕获异常
     *
     * @param request
     * @param uid
     */
    public void log(HttpServletRequest request, Integer uid) throws LoginLogException {
        try {
            // 添加登录日志
            LoginLog loginLog = new LoginLog();
            loginLog.setIp(getIpAddress(request));
            loginLog.setUid(uid);
            loginLogMapper.insertSelective(loginLog);

            // 更新 user 表时间
            User user = new User();
            user.setUid(uid);
            user.setLastlogin(new Date());
            userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            throw new LoginLogException("登录日志更新异常 UID = " + uid);
        }
    }


    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }
}
