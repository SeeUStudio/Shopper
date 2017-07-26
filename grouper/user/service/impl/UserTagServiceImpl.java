package com.seeu.grouper.user.service.impl;

import com.seeu.grouper.user.dao.UserTagMapper;
import com.seeu.grouper.user.model.UserTag;
import com.seeu.grouper.user.service.UserTagService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/08.
 */
@Service
@Transactional
public class UserTagServiceImpl extends AbstractService<UserTag> implements UserTagService {
    @Resource
    private UserTagMapper userTagMapper;

}
