package com.seeu.grouper.store.web;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.store.model.StoreDetail;
import com.seeu.grouper.store.service.StoreDetailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neoxiaoyi on 2017/07/15.
 */
@RestController
@RequestMapping("/api/store/detail")
public class StoreDetailController {
    @Resource
    private StoreDetailService storeDetailService;

    @PostMapping("/update")
    public Result update(StoreDetail storeDetail, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (storeDetail != null && storeDetail.getTid() != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            storeDetail.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        storeDetailService.update(storeDetail);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam("tid") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        StoreDetail storeDetail = storeDetailService.findById(id);
        if (storeDetail == null) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(storeDetail);
    }
}
