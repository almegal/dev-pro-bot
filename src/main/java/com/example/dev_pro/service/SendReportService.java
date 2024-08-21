package com.example.dev_pro.service;

import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Report;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;

public interface SendReportService {

    String savePhoto(PhotoSize[] photo);

    Report createReport(String photoFilePath, String textReport, Adopter adopter, Pet pet);
}
