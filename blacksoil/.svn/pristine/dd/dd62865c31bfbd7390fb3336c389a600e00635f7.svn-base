package com.ali.retail.config;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class EmailSession {


    private static String account = "企业邮箱账户";// 登录账户
    private static String password = "企业邮箱密码";// 登录密码
    private static String host = "smtp.exmail.qq.com";// 服务器地址
    private static String port = "465";// 端口
    private static String protocol = "smtp";// 协议

    //初始化参数
    public static Session init() {
            Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", protocol);
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.port", port);
            // 使用smtp身份验证
            properties.put("mail.smtp.auth", "true");
            // 使用SSL,企业邮箱必需 start
            // 开启安全协议
            MailSSLSocketFactory mailSSLSocketFactory = null;
            try {
                mailSSLSocketFactory = new MailSSLSocketFactory();
                mailSSLSocketFactory.setTrustAllHosts(true);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            properties.put("mail.smtp.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
            properties.put("mail.smtp.socketFactory.port", port);
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(account, password);
                }
            });
            // 使用SSL,企业邮箱必需 end
            // TODO 显示debug信息 正式环境注释掉
            session.setDebug(true);
            return session;
    }
}
