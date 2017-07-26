package com.seeu.grouper.pay.service.impl;

import com.seeu.grouper.pay.dao.PayAddressMapper;
import com.seeu.grouper.pay.model.PayAddress;
import com.seeu.grouper.pay.service.PayAddressService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/07/08.
 */
@Service
@Transactional
public class PayAddressServiceImpl extends AbstractService<PayAddress> implements PayAddressService {
    @Resource
    private PayAddressMapper payAddressMapper;

}
