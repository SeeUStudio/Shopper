package com.seeu.grouper.user.web;
import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.user.model.UserHobby;
import com.seeu.grouper.user.service.UserHobbyService;
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
@RequestMapping("/api/user/hobby")
public class UserHobbyController {
    @Resource
    private UserHobbyService userHobbyService;

    @PostMapping("/update")
    public Result update(UserHobby userHobby, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if ( userHobby != null ){
            // 因为只能修改自己的数据，所以默认改为 uid
            userHobby.setUid(uid);
        }else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        userHobbyService.update(userHobby);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam(value = "id",required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (id == null) {
            id = uid;
        }
        UserHobby userHobby = userHobbyService.findById(id);
        if( userHobby == null ){
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(userHobby);
    }
}
