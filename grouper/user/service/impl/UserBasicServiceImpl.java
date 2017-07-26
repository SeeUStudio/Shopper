package com.seeu.grouper.user.service.impl;

import com.seeu.grouper.login.model.User;
import com.seeu.grouper.user.dao.UserBasicMapper;
import com.seeu.grouper.user.model.UserBasic;
import com.seeu.grouper.user.service.UserBasicService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/19.
 */
@Service
@Transactional
public class UserBasicServiceImpl extends AbstractService<UserBasic> implements UserBasicService {
    @Resource
    private UserBasicMapper userBasicMapper;

    @Override
    public void insertNewUser(User user) {

    }
}
