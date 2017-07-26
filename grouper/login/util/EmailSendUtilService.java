package com.seeu.grouper.login.util;

import com.seeu.configurer.DOMAIN;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by neo on 20/07/2017.
 */
@Service
public class EmailSendUtilService {


    @Autowired
    DOMAIN domain;

    public boolean sendToken(String who, String token) throws EmailException {
        String text = "<div>欢迎使用 Jua 平台<br/>请点击下面链接完成注册：<br/>" +
                "<div style='display:block;'><br/><hr/><br/><a href='" + domain.getDomain() + "/api/regist/callback/" + token + "'> " + domain.getDomain() + "/api/regist/callback/" + token + "<a/>" +
                "</div><br/><br/>如果不能打开链接，请复制该链接在浏览器内打开<br/><hr/><br/>西柚科技敬上<br/>" +
                new Date().toString() + "</div>";
        send(who, text);
        return true;
    }


    private void send(String who, String text) throws EmailException {

        boolean isSSL = true;
        String host = "smtp.163.com";
        int port = 465;
        String from = "seeucoco@163.com";
        String to = who;
        String username = "seeucoco@163.com";
        String password = "SEEUcoco000";

        try {
            Email email = new SimpleEmail();
            email.setSSLOnConnect(isSSL);
            email.setHostName(host);
            email.setSmtpPort(port);
            email.setAuthentication(username, password);
            email.setFrom(from);
            email.addTo(to);
            email.setSubject("主题");
//            email.setMsg(text);
            email.setContent(text, "text/html; charset=UTF-8");
            email.send();
        } catch (EmailException e) {
//            e.printStackTrace();
            throw e;
        }

        System.out.println("发送完毕！");
    }
}
