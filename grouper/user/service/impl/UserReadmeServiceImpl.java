package com.seeu.grouper.user.service.impl;

import com.seeu.grouper.user.dao.UserReadmeMapper;
import com.seeu.grouper.user.model.UserReadme;
import com.seeu.grouper.user.service.UserReadmeService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/08.
 */
@Service
@Transactional
public class UserReadmeServiceImpl extends AbstractService<UserReadme> implements UserReadmeService {
    @Resource
    private UserReadmeMapper userReadmeMapper;

}
