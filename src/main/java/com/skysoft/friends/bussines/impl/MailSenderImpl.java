package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderImpl implements MailSender {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(String messageBody, String targetEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(targetEmail);
        message.setSubject("Confirmation code");
        message.setText(messageBody);
        javaMailSender.send(message);
    }
}
