package com.skysoft.friends.bussines.api;

public interface MailSender {

    void sendMessage(String messageBody, String targetEmail);
}
