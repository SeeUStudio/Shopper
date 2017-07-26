package com.seeu.grouper.user.web;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.grouper.pay.model.PayAddress;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.user.model.UserTag;
import com.seeu.grouper.user.service.UserTagService;
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
 * <p>
 * 操作只有 list、查看、增加、删除，不存在修改方法
 */
@RestController
@RequestMapping("/api/user/tag")
public class UserTagController {
    @Resource
    private UserTagService userTagService;

    @PostMapping("/add")
    public Result add(UserTag userTag, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (userTag != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            userTag.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        userTagService.save(userTag);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        Condition condition = new Condition(UserTag.class);
        condition.createCriteria().andCondition("id = " + id + " AND uid = " + uid);
        userTagService.deleteByCondition(condition);
        return ResultGenerator.genSuccessResult();
    }

//    @PostMapping("/update")
//    public Result update(UserTag userTag, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
//        if (!OAuthType.isAvailable(type)) {
//            return ResultGenerator.genNoAuthResult("无权操作");
//        }
//        if (userTag != null) {
//            // 因为只能修改自己的数据，所以默认改为 uid
//            userTag.setUid(uid);
//        } else {
//            return ResultGenerator.genFailResult("传入参数不完整");
//        }
//        userTagService.update(userTag);
//        return ResultGenerator.genSuccessResult();
//    }

    @PostMapping("/detail")
    public Result detail(@RequestParam(value = "id", required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (id == null) {
            id = uid;
        }
        UserTag userTag = userTagService.findById(id);
        if (userTag == null) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(userTag);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(value = "uid", required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (id == null) {
            id = uid;
        }
        Condition condition = new Condition(UserTag.class);
        condition.createCriteria().andCondition("uid = " + id);
        List<UserTag> userTags = userTagService.findByCondition(condition);
        if (userTags == null || userTags.isEmpty()) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(userTags);
    }
}
