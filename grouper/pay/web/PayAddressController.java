package com.seeu.grouper.pay.web;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.pay.model.PayAddress;
import com.seeu.grouper.pay.service.PayAddressService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neoxiaoyi on 2017/07/08.
 */
@RestController
@RequestMapping("/api/pay/address")
public class PayAddressController {
    @Resource
    private PayAddressService payAddressService;

    @PostMapping("/add")
    public Result add(PayAddress payAddress, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (payAddress != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            payAddress.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        payAddressService.save(payAddress);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam("id") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        payAddressService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(PayAddress payAddress, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (payAddress != null && payAddress.getId() != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            payAddress.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }

        Condition condition = new Condition(PayAddress.class);
        condition.createCriteria().andCondition("id = " + payAddress.getId() + " AND uid = " + uid);


        payAddressService.updateCondition(payAddress, condition);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam("id") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        PayAddress payAddress = payAddressService.findById(id);
        if (payAddress == null) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(payAddress);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(value = "uid", required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (id == null) {
            id = uid;
        }
        Condition condition = new Condition(PayAddress.class);
        condition.createCriteria().andCondition("uid = " + id);
        List<PayAddress> payAddress = payAddressService.findByCondition(condition);
        if (payAddress == null || payAddress.isEmpty()) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(payAddress);
    }


    /**
     * 设为常用地址
     *
     * @param id
     * @param uid
     * @param type
     * @return
     */
    @Transactional
    @PostMapping("/mark")
    public Result star(@RequestParam(value = "id") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }

        PayAddress payAddress = new PayAddress();

        // 其余的改为 0
        payAddress.setId(id);
        payAddress.setLabel(0);

        Condition condition2 = new Condition(PayAddress.class);
        condition2.createCriteria().andCondition("id != " + id + " AND uid = " + uid);

        payAddressService.updateCondition(payAddress, condition2);


        // 标记的改为 1
        payAddress.setId(id);
        payAddress.setLabel(1);

        Condition condition = new Condition(PayAddress.class);
        condition.createCriteria().andCondition("id = " + id + " AND uid = " + uid);

        payAddressService.updateCondition(payAddress, condition);

        return ResultGenerator.genSuccessResult();
    }

}
