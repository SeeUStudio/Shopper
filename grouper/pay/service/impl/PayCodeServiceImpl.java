package com.seeu.grouper.pay.service.impl;

import com.seeu.grouper.pay.dao.PayCodeMapper;
import com.seeu.grouper.pay.model.PayCode;
import com.seeu.grouper.pay.service.PayCodeService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/08.
 */
@Service
@Transactional
public class PayCodeServiceImpl extends AbstractService<PayCode> implements PayCodeService {
    @Resource
    private PayCodeMapper payCodeMapper;

}
