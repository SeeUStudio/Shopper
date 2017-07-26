package com.seeu.grouper.store.service.impl;

import com.seeu.grouper.store.dao.StoreImgMapper;
import com.seeu.grouper.store.model.StoreImg;
import com.seeu.grouper.store.service.StoreImgService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/16.
 */
@Service
@Transactional
public class StoreImgServiceImpl extends AbstractService<StoreImg> implements StoreImgService {
    @Resource
    private StoreImgMapper storeImgMapper;

}
