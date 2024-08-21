package com.example.dev_pro.service;

import com.example.dev_pro.model.Report;
import com.pengrad.telegrambot.model.Message;

public interface SendReportService {

    Report handleReport(Message message);

}
