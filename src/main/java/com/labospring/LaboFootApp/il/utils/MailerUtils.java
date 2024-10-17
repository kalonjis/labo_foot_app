package com.labospring.LaboFootApp.il.utils;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MailerUtils {

    @Value("${spring.mail.username")
    private String appEmailAddress;

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendMail(String subject, String templateName, Context context, String...to){
        String htmlContent = templateEngine.process("emails/" +  templateName, context);

        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom(appEmailAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }
}
