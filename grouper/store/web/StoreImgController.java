package com.seeu.grouper.store.web;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.grouper.store.service.StoreService;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.store.model.StoreImg;
import com.seeu.grouper.store.service.StoreImgService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neoxiaoyi on 2017/07/16.
 */
@RestController
@RequestMapping("/api/store/img")
public class StoreImgController {
    @Resource
    private StoreImgService storeImgService;
    @Resource
    private StoreService storeService;

    @PostMapping("/add")
    public Result add(StoreImg storeImg, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (storeImg != null && storeImg.getTid() != null && storeImg.getImg() != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            storeImg.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        // 查询数据库
        Condition condition = new Condition(StoreImg.class);
        condition.createCriteria().andCondition("tid = " + storeImg.getTid() + " AND uid = " + uid);
        condition.orderBy("sortex");
        List<StoreImg> storeImgs = storeImgService.findByCondition(condition);

        // 如果用户只上传了一张图片
        if (storeImgs.size() == 0) {
            // 更新 store 表 img 信息
            storeService.updateImgInfo(storeImg.getTid(), uid, storeImg.getImg());
        }
        // 设定 index [ 0,1,2,3,4,5 ] 用 0 做计数，0 为默认值，预览图片的序号id
        storeImg.setSortex(storeImgs.size());
        storeImgService.save(storeImg);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam("id") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        Condition condition = new Condition(StoreImg.class);
        condition.createCriteria().andCondition("id = " + id + " AND uid = " + uid);
        storeImgService.deleteByCondition(condition);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(StoreImg storeImg, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (storeImg != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            storeImg.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        storeImgService.update(storeImg);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam("id") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        StoreImg storeImg = storeImgService.findById(id);
        if (storeImg == null) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(storeImg);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(value = "tid", required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        Condition condition = new Condition(StoreImg.class);
        condition.createCriteria().andCondition("tid = " + id);
        condition.orderBy("sortex");
        List<StoreImg> storeImgs = storeImgService.findByCondition(condition);
        if (storeImgs == null || storeImgs.isEmpty()) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(storeImgs);
    }

    /**
     * 重新排序后，将第 0 号图片更新为预览页面的图片
     *
     * @param sortList 长这样： 0|1|2|4|3|5
     * @param uid
     * @param type
     * @return
     */
    @PostMapping("resort")
    public Result resort(@RequestParam("tid") Integer tid, @RequestParam("sortList") String sortList, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }

        if (!sortList.contains("0")) {// 必须包含 0
            return ResultGenerator.genFailResult("参数错误");
        }
        if (!sortList.contains(",") && !sortList.equals("0")) { // 无分隔符"逗号"，则必须为 0
            return ResultGenerator.genFailResult("参数错误");
        }

        String[] sorts = sortList.split(",");

        // 转换为数字 [ 注意异常，此处直接抛出 ]
        int resortIndex[] = new int[sorts.length];
        try {
            for (int i = 0; i < sorts.length; i++) {
                resortIndex[i] = Integer.parseInt(sorts[i]);
            }
        } catch (NumberFormatException e) {
            return ResultGenerator.genExceptionResult("传入数据必须为数字");
        }

        // 查询数据库
        Condition condition = new Condition(StoreImg.class);
        condition.createCriteria().andCondition("tid = " + tid + " AND uid = " + uid);
        condition.orderBy("sortex");
        List<StoreImg> storeImgs = storeImgService.findByCondition(condition);

        if (storeImgs.size() != sorts.length) {
            return ResultGenerator.genFailResult("排序数据长度错误");
        }

        // 更新数据库
        if (storeImgs != null && storeImgs.size() == sorts.length) {
            for (int i = 0; i < storeImgs.size(); i++) {
                StoreImg img = storeImgs.get(i);
                img.setSortex(resortIndex[i]);
                storeImgService.update(img);
                if (resortIndex[i] == 0) {
                    // 更新 store 表 img 信息
                    storeService.updateImgInfo(tid, uid, img.getImg());
                }
            }
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("未知错误");
    }
}
