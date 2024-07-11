package com.example.dev_pro.service;

public interface CommandHandlerService {
    String handleCommand(Long chatId, String text);
}
