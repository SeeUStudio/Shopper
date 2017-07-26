package com.seeu.grouper.store.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.grouper.store.model.StoreDetail;
import com.seeu.grouper.store.service.StoreDetailService;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.store.model.Store;
import com.seeu.grouper.store.service.StoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neoxiaoyi on 2017/07/15.
 */
@RestController
@RequestMapping("/api/store")
public class StoreController {
    @Resource
    private StoreService storeService;
    @Resource
    private StoreDetailService storeDetailService;

    @PostMapping("/add")
    public Result add(Store store, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (store != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            store.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        ArrayList<Store> list = new ArrayList<>();
        list.add(store);
        storeService.insertSelective2(store);// save 后可以获得主键值
        // 添加 detail 信息
        StoreDetail detail = new StoreDetail();
        detail.setTid(store.getTid());
        detail.setUid(uid);
        storeDetailService.save(detail);

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 软删除
     *
     * @param id
     * @param uid
     * @param type
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam("tid") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        Store store = new Store();
        store.setTid(id);
        store.setUid(uid);
        store.setStatus(-1);// -1 软删除标识
        Condition condition = new Condition(Store.class);
        condition.createCriteria().andCondition("tid = " + id + " AND uid = " + uid);
        storeService.updateCondition(store, condition);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Store store, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (store != null) {
            // 因为只能修改自己的数据，所以默认改为 uid
            store.setUid(uid);
        } else {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        Condition condition = new Condition(Store.class);
        condition.createCriteria().andCondition("tid = " + store.getTid() + " AND uid = " + uid + " AND status != -1");
        storeService.updateCondition(store, condition);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam("tid") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        Condition condition = new Condition(Store.class);
        condition.createCriteria().andCondition("tid = " + id + " AND status != -1");
        List<Store> store = storeService.findByCondition(condition);
        if (store == null || store.isEmpty()) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        return ResultGenerator.genSuccessResult(store.get(0));// 只需要返回一条数据即可，不可能有多条～
    }

    /**
     * 查询私人 uid 的商品货架信息
     *
     * @param page
     * @param size
     * @param orderby 可选 price、maxJoin、viewTime、createTime 等
     * @param sort    可选 desc
     *                <p>
     *                以下参数系统自动注入
     * @param id
     * @param uid
     * @param type
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("orderby") String orderby, @RequestParam("sort") String sort, @RequestParam(value = "uid",required = false) Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (id == null) {
            id = uid;
        }
        PageHelper.startPage(page, size);
        // order
        if (!sort.equals("desc")) {
            sort = "";
        }
        switch (orderby) {
            case "price":
                PageHelper.orderBy("price " + sort);
                break;
            case "maxJoin":
                PageHelper.orderBy("max_join " + sort);
                break;
            case "viewTime":
                PageHelper.orderBy("view_time " + sort);
                break;
            case "createTime":
                PageHelper.orderBy("create_time " + sort);
                break;
            default:
                PageHelper.orderBy("tid " + sort);
                break;
        }
        Condition condition = new Condition(Store.class);
        condition.createCriteria().andCondition("uid = " + id + " AND status != -1");
        List<Store> stores = storeService.findByCondition(condition);
        if (stores == null || stores.isEmpty()) {
            return ResultGenerator.genNoContentResult("查询结果为空");
        }
        PageInfo pageInfo = new PageInfo(stores);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 商品状态
     * 0 未上架
     * 1 已上架
     * 2 完成交易
     * -1 删除
     */
    @PostMapping("/publish")
    public Result publishStore(@RequestParam("tid") Integer tid, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        // 修改 tid 和 uid 匹配的数据 status 为 1
        Condition condition = new Condition(Store.class);
        condition.createCriteria().andCondition("tid = " + tid + " AND uid = " + uid);
        List<Store> stores = storeService.findByCondition(condition);
        if (stores.size() != 0) {
            Store store = stores.get(0);
            if (store.getStatus() == -1) {
                return ResultGenerator.genFailResult("发布失败，商品已被删除");
            } else if (store.getStatus() == 2) {
                return ResultGenerator.genFailResult("发布失败，商品已交易完成并被彻底下架");
            } else if (store.getStatus() == 0) {
                // 上架
                Condition condition2 = new Condition(Store.class);
                condition2.createCriteria().andCondition("tid = " + tid + " AND uid = " + uid);
                Store store2 = new Store();
                store2.setTid(store.getTid());
                store2.setStatus(1);
                storeService.updateCondition(store2, condition2);
                return ResultGenerator.genSuccessResult();
            } else {
                return ResultGenerator.genExceptionResult("发布失败，商品信息异常");
            }
        } else {
            return ResultGenerator.genNoContentResult("发布失败，无此商品信息");
        }
    }
    @PostMapping("/off")
    public Result offStore(@RequestParam("tid") Integer tid, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
        if (!OAuthType.isAvailable(type)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        // 修改 tid 和 uid 匹配的数据 status 为 0
        Condition condition = new Condition(Store.class);
        condition.createCriteria().andCondition("tid = " + tid + " AND uid = " + uid);
        List<Store> stores = storeService.findByCondition(condition);
        if (stores.size() != 0) {
            Store store = stores.get(0);
            if (store.getStatus() == -1) {
                return ResultGenerator.genFailResult("下架失败，商品已被删除");
            } else if (store.getStatus() == 2) {
                return ResultGenerator.genFailResult("下架失败，商品已交易完成并被彻底下架");
            } else if (store.getStatus() == 1) {
                // 下架
                Condition condition2 = new Condition(Store.class);
                condition2.createCriteria().andCondition("tid = " + tid + " AND uid = " + uid);
                Store store2 = new Store();
                store2.setTid(store.getTid());
                store2.setStatus(0);
                storeService.updateCondition(store2, condition2);
                return ResultGenerator.genSuccessResult();
            } else {
                return ResultGenerator.genExceptionResult("下架失败，商品信息异常");
            }
        } else {
            return ResultGenerator.genNoContentResult("下架失败，无此商品信息");
        }
    }
}
