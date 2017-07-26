package com.seeu.grouper.user.web;
import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.user.model.UserBasic;
import com.seeu.grouper.user.service.UserBasicService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by neoxiaoyi on 2017/07/19.
*/
@RestController
@RequestMapping("/api/user/basic")
public class UserBasicController {
    @Resource
    private UserBasicService userBasicService;

    @PostMapping("/update")
    public Result update(UserBasic userBasic, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (userBasic != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            userBasic.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        userBasicService.update(userBasic);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam(value = "id", required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (id == null) {
            id = uid;
        }
        UserBasic userBasic = userBasicService.findById(id);
        if (userBasic == null) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(userBasic);
    }

    @PostMapping("/login-info")
    public Result info(@RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("该用户为访客");
        }
        UserBasic userBasic = userBasicService.findById(uid);
        if (userBasic == null) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(userBasic);
    }
}
