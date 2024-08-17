package com.example.dev_pro.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.SendMessage;

public interface PhotoReportService {

    SendMessage handleReport(Long chatId, PhotoSize[] photoSize);
}
