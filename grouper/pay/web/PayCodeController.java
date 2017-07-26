package com.seeu.grouper.pay.web;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.pay.model.PayCode;
import com.seeu.grouper.pay.service.PayCodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neoxiaoyi on 2017/07/08.
 */
@RestController
@RequestMapping("/api/pay/code")
public class PayCodeController {
    @Resource
    private PayCodeService payCodeService;

//    目前不提供增加支付方式的接口
//    @PostMapping("/add")
//    public Result add(PayCode payCode) {
//        payCodeService.save(payCode);
//        return ResultGenerator.genSuccessResult();
//    }
//

    @PostMapping("/update")
    public Result update(PayCode payCode, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (payCode != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            payCode.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        payCodeService.update(payCode);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam(value = "uid", required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (id == null) {
            id = uid;
        }
        PayCode payCode = payCodeService.findById(id);
        if (payCode == null) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(payCode);
    }
}
