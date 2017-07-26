package com.seeu.grouper.store.service.impl;

import com.seeu.grouper.store.dao.StoreTagMapper;
import com.seeu.grouper.store.model.StoreTag;
import com.seeu.grouper.store.service.StoreTagService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/15.
 */
@Service
@Transactional
public class StoreTagServiceImpl extends AbstractService<StoreTag> implements StoreTagService {
    @Resource
    private StoreTagMapper storeTagMapper;

}
