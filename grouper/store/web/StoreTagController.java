package com.seeu.grouper.store.web;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.store.model.StoreTag;
import com.seeu.grouper.store.service.StoreTagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neoxiaoyi on 2017/07/15.
 * 操作只有 list、查看、增加、删除，不存在修改方法
 */
@RestController
@RequestMapping("/api/store/tag")
public class StoreTagController {
    @Resource
    private StoreTagService storeTagService;

    @PostMapping("/add")
    public Result add(StoreTag storeTag, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (storeTag != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            storeTag.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        storeTagService.save(storeTag);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 未做软删除 v0.1
     *
     * @param id
     * @param uid
     * @param type
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam("id") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        Condition condition = new Condition(StoreTag.class);
        condition.createCriteria().andCondition("id = " + id + " AND uid = " + uid);
        storeTagService.deleteByCondition(condition);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/detail")
    public Result detail(@RequestParam("id") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        StoreTag storeTag = storeTagService.findById(id);
        if (storeTag == null) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(storeTag);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(value = "tid",required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        Condition condition = new Condition(StoreTag.class);
        condition.createCriteria().andCondition("tid = " + id);
        List<StoreTag> storeTags = storeTagService.findByCondition(condition);
        if (storeTags == null || storeTags.isEmpty()) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(storeTags);
    }
}
