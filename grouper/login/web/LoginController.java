package com.seeu.grouper.login.web;

import com.alibaba.fastjson.JSONObject;
import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.core.ServiceException;
import com.seeu.grouper.login.service.LoginLogService;
import com.seeu.grouper.login.util.LoginLogException;
import com.seeu.oauth.JwtConstant;
import com.seeu.oauth.JwtUtil;
import com.seeu.oauth.OAuthType;
import com.seeu.grouper.login.model.User;
import com.seeu.grouper.login.service.RegistService;
import com.seeu.oauth.SignTokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by neoxiaoyi on 2017/07/18.
 */
@RestController
@RequestMapping("/api")
public class LoginController {
    @Resource
    private RegistService userService;
    @Resource
    private LoginLogService loginLogService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtConstant jwtConstant;


    @PostMapping("/login")
    public Result login(User user, HttpServletRequest request) {
        // 不需要验证 type，任何人都可以登录
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        // check
        User realUser = userService.findBy("email", user.getEmail());
        if (realUser == null) {
            return ResultGenerator.genFailResult("账号或密码错误");// 无此用户
        }
        if (realUser.getPassword().equals(user.getPassword())) {
            // 验证成功，生成 TOKEN
            SignTokenUser userToken = new SignTokenUser();
            userToken.setUid("" + realUser.getUid());
            userToken.setType(OAuthType.SEEU);
            userToken.setExtra("");
            try {
                String subject = jwtUtil.generalSubject(userToken);
                String token = jwtUtil.createJWT(jwtConstant.getJWT_ID(), subject, jwtConstant.getJWT_INTERVAL());
                // 添加登录日志，并更新两个表的登录时间（ user 的最近登录时间、login_log 的记录增添 ）
                try {
                    loginLogService.log(request, realUser.getUid());
                } catch (LoginLogException logE) {
                    // 日志添加异常，暂不做任何处理
                }
                JSONObject jo = new JSONObject();
                jo.put("token", token);
                jo.put("uid", realUser.getUid());
                jo.put("email", realUser.getEmail());
                jo.put("lastLogin", realUser.getLastlogin());
                return ResultGenerator.genSuccessResult(jo);
            } catch (Exception e) {
                throw new ServiceException("换取签名 TOKEN 失败");
            }
        } else {
            return ResultGenerator.genFailResult("账号或密码错误");// 无此用户
        }
    }
}
