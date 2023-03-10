package com.example.sale;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author xiaowenrou
 * @date 2023/2/6
 */
@SpringBootTest
public class MailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ITemplateEngine engine;

    @Test
    @SneakyThrows
    public void sendTest() {
        var message = new MimeMessageHelper(this.javaMailSender.createMimeMessage(), true);
        message.setTo("604106138@qq.com");
        message.setFrom("604106138@qq.com");
        message.setSubject("测试邮件");
        Context ctx = new Context();
        ctx.setVariable("sender", "1");
        ctx.setVariable("address", "2");
        ctx.setVariable("content", "3");
        ctx.setVariable("current", "4");
        ctx.setVariable("location", "http://www.baidu.com");
        String s = this.engine.process("mail", ctx);
        //message.setText(this.engine.process("mail", ctx), true);
        //this.javaMailSender.send(message.getMimeMessage());
        System.out.println(s);
    }

}
