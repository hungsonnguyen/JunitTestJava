package com.zeusbe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailSenderServiceImpl {
    @Autowired
    private JavaMailSender javaMailSender;


    public boolean sendEmail(String toEmail, String subject, String code) {
        boolean multipart = true;
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            String body = " là mã xác nhận cấp lại mật khẩu trong swap-now. Hay nhập mã trên swapnow để xác minh tài khoản của bạn ";
            helper = new MimeMessageHelper(message, multipart, "utf-8");
            String htmlMsg = body + code;
            helper.setText("Thành công", htmlMsg);


//            Email receiver
            helper.setTo("hungson.civilengineering@gmail.com");

            helper.setSubject("Nhận mã OTP ");

            this.javaMailSender.send(message);
            System.out.println("Gửi mã OTP thành công");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
