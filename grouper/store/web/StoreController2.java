//package com.seeu.grouper.store.web;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.seeu.core.Result;
//import com.seeu.core.ResultGenerator;
//import com.seeu.grouper.store.model.StoreDetail;
//import com.seeu.grouper.store.service.StoreDetailService;
//import com.seeu.oauth.OAuthType;
//import com.seeu.grouper.store.model.Store;
//import com.seeu.grouper.store.service.StoreService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestAttribute;
//import org.springframework.web.bind.annotation.RequestParam;
//import tk.mybatis.mapper.entity.Condition;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by neoxiaoyi on 2017/07/15.
// */
//@RestController
//@RequestMapping("/api/store")
//public class StoreController2 {
//    @Resource
//    private StoreService storeService;
//    @Resource
//    private StoreDetailService storeDetailService;
//
//    @PostMapping("/add")
//    public Result add(Store store, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
//        if (!OAuthType.isAvailable(type)) {
//            return ResultGenerator.genNoAuthResult("无权操作");
//        }
//        if (store != null) {
//            // 因为只能修改自己的数据，所以默认改为 uid
//            store.setUid(uid);
//        } else {
//            return ResultGenerator.genFailResult("传入参数不完整");
//        }
//        ArrayList<Store> list = new ArrayList<>();
//        list.add(store);
//        storeService.insertSelective2(store);// save 后可以获得主键值
//        // 添加 detail 信息
//        StoreDetail detail = new StoreDetail();
//        detail.setTid(store.getTid());
//        detail.setUid(uid);
//        storeDetailService.save(detail);
//
//        return ResultGenerator.genSuccessResult();
//    }
//
//    /**
//     * 软删除
//     *
//     * @param id
//     * @param uid
//     * @param type
//     * @return
//     */
//    @PostMapping("/delete")
//    public Result delete(@RequestParam("tid") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
//        if (!OAuthType.isAvailable(type)) {
//            return ResultGenerator.genNoAuthResult("无权操作");
//        }
//        Store store = new Store();
//        store.setTid(id);
//        store.setUid(uid);
//        store.setStatus(-1);// -1 软删除标识
//        Condition condition = new Condition(Store.class);
//        condition.createCriteria().andCondition("tid = " + id + " AND uid = " + uid);
//        storeService.updateCondition(store, condition);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @PostMapping("/update")
//    public Result update(Store store, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
//        if (!OAuthType.isAvailable(type)) {
//            return ResultGenerator.genNoAuthResult("无权操作");
//        }
//        if (store != null) {
//            // 因为只能修改自己的数据，所以默认改为 uid
//            store.setUid(uid);
//        } else {
//            return ResultGenerator.genFailResult("传入参数不完整");
//        }
//        Condition condition = new Condition(Store.class);
//        condition.createCriteria().andCondition("tid = " + store.getTid() + " AND uid = " + uid + " AND status != -1");
//        storeService.updateCondition(store, condition);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @PostMapping("/detail")
//    public Result detail(@RequestParam("tid") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
//        Condition condition = new Condition(Store.class);
//        condition.createCriteria().andCondition("tid = " + id + " AND status != -1");
//        List<Store> store = storeService.findByCondition(condition);
//        if (store == null || store.isEmpty()) {
//            return ResultGenerator.genNoContentResult("查询结果为空");
//        }
//        return ResultGenerator.genSuccessResult(store.get(0));// 只需要返回一条数据即可，不可能有多条～
//    }
//
//    /**
//     * 查询 uid 的商品货架信息
//     *
//     * @param page
//     * @param size
//     * @param orderby 可选 price、maxJoin、viewTime、createTime 等
//     * @param sort    可选 desc
//     *                <p>
//     *                以下参数系统自动注入
//     * @param id
//     * @param uid
//     * @param type
//     * @return
//     */
//    @PostMapping("/list")
//    public Result list(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("orderby") String orderby, @RequestParam("sort") String sort, @RequestParam("uid") Integer id, @RequestAttribute("uid") Integer uid, @RequestAttribute("type") String type) {
//        if (id == null) {
//            id = uid;
//        }
//        PageHelper.startPage(page, size);
//        // order
//        if (!sort.equals("desc")) {
//            sort = "";
//        }
//        switch (orderby) {
//            case "price":
//                PageHelper.orderBy("price " + sort);
//                break;
//            case "maxJoin":
//                PageHelper.orderBy("max_join " + sort);
//                break;
//            case "viewTime":
//                PageHelper.orderBy("view_time " + sort);
//                break;
//            case "createTime":
//                PageHelper.orderBy("create_time " + sort);
//                break;
//            default:
//                PageHelper.orderBy("tid " + sort);
//                break;
//        }
//        Condition condition = new Condition(Store.class);
//        condition.createCriteria().andCondition("uid = " + id + " AND status != -1");
//        List<Store> stores = storeService.findByCondition(condition);
//        if (stores == null || stores.isEmpty()) {
//            return ResultGenerator.genNoContentResult("查询结果为空");
//        }
//        PageInfo pageInfo = new PageInfo(stores);
//        return ResultGenerator.genSuccessResult(pageInfo);
//    }
//
//}
