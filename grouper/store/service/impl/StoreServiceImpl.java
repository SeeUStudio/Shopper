package com.seeu.grouper.store.service.impl;

import com.seeu.grouper.store.dao.StoreMapper;
import com.seeu.grouper.store.model.Store;
import com.seeu.grouper.store.model.StoreImg;
import com.seeu.grouper.store.service.StoreService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/15.
 */
@Service
@Transactional
public class StoreServiceImpl extends AbstractService<Store> implements StoreService {
    @Resource
    private StoreMapper storeMapper;

    @Override
    public void insertSelective2(Store store) {
        storeMapper.insertSelective2(store);
    }

    @Override
    public void updateImgInfo(Integer tid, Integer uid, String imgURL) {
        Store store = new Store();
        store.setTid(tid);
        store.setUid(uid);
        store.setImg(imgURL);
        Condition condition = new Condition(StoreImg.class);
        condition.createCriteria().andCondition("tid = " + tid + " AND uid = " + uid);
        storeMapper.updateByConditionSelective(store, condition);
    }


}
