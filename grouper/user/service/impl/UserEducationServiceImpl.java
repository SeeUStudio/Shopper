package com.seeu.grouper.user.service.impl;

import com.seeu.grouper.user.dao.UserEducationMapper;
import com.seeu.grouper.user.model.UserEducation;
import com.seeu.grouper.user.service.UserEducationService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/08.
 */
@Service
@Transactional
public class UserEducationServiceImpl extends AbstractService<UserEducation> implements UserEducationService {
    @Resource
    private UserEducationMapper userEducationMapper;

}
