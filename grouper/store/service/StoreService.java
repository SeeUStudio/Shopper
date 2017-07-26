package com.seeu.grouper.store.service;

import com.seeu.grouper.store.model.Store;
import com.seeu.core.Service;


/**
 * Created by neoxiaoyi on 2017/07/15.
 */
public interface StoreService extends Service<Store> {
    public void insertSelective2(Store store);

    public void updateImgInfo(Integer tid, Integer uid, String imgURL);
}
