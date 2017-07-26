package com.seeu.grouper.store.service.impl;

import com.seeu.grouper.store.dao.StoreDetailMapper;
import com.seeu.grouper.store.model.StoreDetail;
import com.seeu.grouper.store.service.StoreDetailService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/15.
 */
@Service
@Transactional
public class StoreDetailServiceImpl extends AbstractService<StoreDetail> implements StoreDetailService {
    @Resource
    private StoreDetailMapper storeDetailMapper;

}
