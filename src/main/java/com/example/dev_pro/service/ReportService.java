package com.example.dev_pro.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

public interface ReportService{

    SendMessage handleReport(Long chatId, String[] text);
}
