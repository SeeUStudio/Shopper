package com.seeu.grouper.login.web;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.grouper.login.model.User;
import com.seeu.grouper.login.service.RegistService;
import com.seeu.grouper.login.util.EmailSendUtilService;
import com.seeu.grouper.pay.model.PayCode;
import com.seeu.grouper.pay.service.PayCodeService;
import com.seeu.grouper.user.model.UserBasic;
import com.seeu.grouper.user.model.UserEducation;
import com.seeu.grouper.user.model.UserHobby;
import com.seeu.grouper.user.model.UserReadme;
import com.seeu.grouper.user.service.UserBasicService;
import com.seeu.grouper.user.service.UserEducationService;
import com.seeu.grouper.user.service.UserHobbyService;
import com.seeu.grouper.user.service.UserReadmeService;
import com.seeu.oauth.JwtConstant;
import com.seeu.oauth.JwtUtil;
import com.seeu.oauth.OAuthType;
import com.seeu.oauth.SignTokenUser;
import org.apache.commons.mail.EmailException;
import org.apache.ibatis.mapping.ResultFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by neoxiaoyi on 2017/07/18.
 * 注册：
 * 初始化表：
 * user
 * user_basic
 * user_education
 * user_hobby
 * user_readme
 * pay_code
 * <p>
 * 对于表：
 * user_tag
 * pay_address
 * 可以不用初始化，因为数据是多条信息并提供了 add 接口
 */
@RestController
@RequestMapping("/api")
public class RegistController {
    @Resource
    private RegistService registService;

    @Resource
    private UserBasicService userBasicService;
    @Resource
    private UserEducationService userEducationService;
    @Resource
    private UserHobbyService userHobbyService;
    @Resource
    private UserReadmeService userReadmeService;
    @Resource
    private PayCodeService payCodeService;


    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtConstant jwtConstant;

    @Autowired
    EmailSendUtilService emailSendUtilService;

    @PostMapping("/regist")
    public Result regist(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (email.length() == 0 || password.length() == 0) {
            return ResultGenerator.genFailResult("参数错误");
        }
        if (registService.checkPasswordAvailable(password)
                && registService.checkEmailAvailable(email)) {
            if (registService.hasRegisted(email)) {
                return ResultGenerator.genFailResult("该邮箱已被注册，如果确认邮箱正确请申述找回密码");
            }
            // 给予邮箱发送验证
            SignTokenUser mockUser = new SignTokenUser();
            mockUser.setUid(email);
            mockUser.setExtra(password);
            mockUser.setType(OAuthType.REGISTING);
            String token = genMockUserToken(mockUser);
            //把 token 发送邮件给对方
            try {
                emailSendUtilService.sendToken(email, token);
                // 发送成功
                return ResultGenerator.genSuccessResult("邮件发送成功，请点击邮件内链接进行操作");
            } catch (EmailException e) {
                // 发送失败
                return ResultGenerator.genExceptionResult("邮件发送失败，请确认邮箱是否正确");
            }
        } else {
            return ResultGenerator.genFailResult("邮箱或密码格式错误");
        }
    }

    /**
     * 该接口不做任何返回信息，只做重定向（ 如果注册成功则定向到完善个人信息界面，否则弹出注册失败 ）
     *
     * @param ticket
     */
    @GetMapping("/regist/callback/{ticket:.+}")
    public void callbackEmailCheck(@PathVariable("ticket") String ticket, HttpServletRequest request, HttpServletResponse response) {
        // 这是返回过来的邮箱确认信息，检查通过后给予注册
        if (ticket != null) {
            // 验证
            SignTokenUser signTokenUser = jwtUtil.parseToken(ticket);
            if (signTokenUser != null) {
                // 说明 ticket 为真，检查 type 是否为注册中用户
                if (OAuthType.isRegisting(signTokenUser.getType())) {
                    // 注册，数据库添加记录
                    String email = signTokenUser.getUid();      //此处的值为 email
                    String password = signTokenUser.getExtra(); // 此处放置的密码
                    if (email != null && password != null) {
                        if (registService.checkPasswordAvailable(password)
                                && registService.checkEmailAvailable(email)
                                && !registService.hasRegisted(email)) {
                            // 给予注册
                            Integer uid = initUser(email, password);
                            initUserBasic(uid, email);
                            initUserEducation(uid);
                            initUserHobby(uid);
                            initUserReadme(uid);
                            initPayCode(uid);
                            // 注册成功，把 token 传过去
                            SignTokenUser realUser = new SignTokenUser();
                            realUser.setUid("" + uid);
                            realUser.setType(OAuthType.SEEU);
                            realUser.setExtra("new user");
                            String token = genMockUserToken(realUser);

                            // 重定向到结果页
                            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);// 302
                            response.setHeader("Location", "https://jua.store/registcallback.html?token=" + token);
                            return;
                        } else {
                            // 注册失败，可能邮箱已经被使用
                            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);// 302
                            response.setHeader("Location", "https://jua.store/registcallback.html?token=" + "registing-emailregisted");
                            return;
                        }
                    } else {
                        // 重定向到 "信息过期，需要重新注册页面"
                        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);// 302
                        response.setHeader("Location", "https://jua.store/registcallback.html?token=" + "registing-outtime");
                        return;
                    }
                }
            } else {
                // 重定向到 "无该注册信息页面"
                // 重定向到 "信息过期，需要重新注册页面"
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);// 302
                response.setHeader("Location", "https://jua.store/registcallback.html?token=" + "bad");
                return;
            }
        }
    }

    private String genMockUserToken(SignTokenUser mockUser) {
        try {
            String subject = jwtUtil.generalSubject(mockUser);
            String token = jwtUtil.createJWT(jwtConstant.getJWT_ID(), subject, jwtConstant.getJWT_INTERVAL());
            return token;
        } catch (Exception e) {
            return "empty";
        }
    }

    /**
     * 检查是否注册成功，可以每 5 秒刷新一次来访问这个接口
     *
     * @param email
     * @param status 客户端主动传输该值表示已经填表注册正在邮箱确认
     * @return
     */
    @PostMapping("/regist/check")
    public Result checkStatus(@RequestParam("email") String email, @RequestParam(value = "ising", required = false) Integer status) {
        if (email == null) {
            return ResultGenerator.genFailResult("参数不完整");
        }
        // 从数据库搜取用户信息，存在则表示注册成功
        User user = registService.findBy("email", email);
        if (user != null && user.getUid() != null) {
            return ResultGenerator.genSuccessResult();// 注册成功
        } else {
            if (status != null) {
                return ResultGenerator.genNoContentResult("注册中");
            }
        }
        return ResultGenerator.genFailResult("注册失败");
    }

    private Integer initUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setLastlogin(new Date());
        registService.insertSelective2(user);
        return user.getUid();
    }

    private void initUserBasic(Integer uid, String email) {
        // 设定默认 icon、email 即可
        UserBasic basic = new UserBasic();
        basic.setUid(uid);
        basic.setEmail(email);
        basic.setIcon("/seeu/img/userhead.jpg");
        basic.setNickname(email);
        userBasicService.save(basic);
    }

    private void initUserEducation(Integer uid) {
        UserEducation education = new UserEducation();
        education.setUid(uid);
        education.setNote("暂未更新个人教育信息");
        userEducationService.save(education);
    }

    private void initUserHobby(Integer uid) {
        UserHobby hobby = new UserHobby();
        hobby.setUid(uid);
        hobby.setNote("爱好独特，暂时还没更新");
        userHobbyService.save(hobby);
    }

    private void initUserReadme(Integer uid) {
        UserReadme readme = new UserReadme();
        readme.setUid(uid);
        readme.setNote("暂未更新个人介绍");
        userReadmeService.save(readme);
    }

    private void initPayCode(Integer uid) {
        PayCode payCode = new PayCode();
        payCode.setUid(uid);
        payCode.setAlipay("/seeu/img/alipay.png");
        payCode.setWechat("/seeu/img/paywechat.png");
        payCodeService.save(payCode);
    }
}
