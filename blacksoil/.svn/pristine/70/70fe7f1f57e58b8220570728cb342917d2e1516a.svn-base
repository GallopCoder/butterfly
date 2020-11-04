package com.ali.retail.service;

import com.ali.retail.bean.Result;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public static Result send(String smtpHost, String fromEmailAdr, String toEmailAdr, String password) throws Exception{
        Session session = getSession(smtpHost);
        Message msg = createMessage(fromEmailAdr, toEmailAdr, session);
        Transport transport = session.getTransport();
        transport.connect(fromEmailAdr, password);//发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)
        transport.sendMessage(msg, msg.getAllRecipients());
        System.out.println("邮件发送成功...");
        transport.close();
        return null;
    }

    private static Message createMessage(String fromEmailAdr, String toEmailAdr, Session session) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setSubject("数据流预警邮件");
        msg.setText("你好!这是我的第一个javamail程序---");
        msg.setFrom(new InternetAddress(fromEmailAdr));//发件人邮箱(我的163邮箱)
        msg.setRecipient(Message.RecipientType.TO,
                new InternetAddress(toEmailAdr)); //收件人邮箱(我的QQ邮箱)
        msg.saveChanges();
        return msg;
    }

    private static Session getSession(String smtpHost) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host",smtpHost);// smtp服务器地址
        Session session = Session.getInstance(props);
        session.setDebug(true);
        return session;
    }

    public static void main(String[] args) throws Exception {
//        send();
    }
}
