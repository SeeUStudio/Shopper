package com.seeu.grouper.user.service.impl;

import com.seeu.grouper.user.dao.UserHobbyMapper;
import com.seeu.grouper.user.model.UserHobby;
import com.seeu.grouper.user.service.UserHobbyService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/08.
 */
@Service
@Transactional
public class UserHobbyServiceImpl extends AbstractService<UserHobby> implements UserHobbyService {
    @Resource
    private UserHobbyMapper userHobbyMapper;

}
