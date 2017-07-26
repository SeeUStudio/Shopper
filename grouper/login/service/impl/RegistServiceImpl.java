package com.seeu.grouper.login.service.impl;

import com.seeu.grouper.login.dao.UserMapper;
import com.seeu.grouper.login.model.User;
import com.seeu.core.AbstractService;
import com.seeu.grouper.login.service.RegistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by neoxiaoyi on 2017/07/18.
 */
@Service
@Transactional
public class RegistServiceImpl extends AbstractService<User> implements RegistService {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean checkEmailAvailable(String email) {
        //电子邮件
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean checkPasswordAvailable(String password) {
        //密码 长度 6 位以上即可
        return (password != null || !StringUtils.isEmpty(password) || password.length() > 5);
    }

    @Override
    public boolean hasRegisted(String email) {
        // 根据邮箱判断是否注册过
        User user = findBy("email", email);
        return !(user == null || user.getEmail() == null || !user.getEmail().equals(email));
    }

    @Override
    public int insertSelective2(User var1) {
        return userMapper.insertSelective2(var1);
    }

}
