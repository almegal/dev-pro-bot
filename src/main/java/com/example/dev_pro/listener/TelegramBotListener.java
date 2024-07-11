package com.example.dev_pro.listener;

import com.example.dev_pro.service.CommandHandlerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramBotListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final CommandHandlerService commandHandlerService;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream()
                    .filter(update -> update !=null)
                    .forEach(this::commandProcessing);
        } catch (Exception e) {

        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void commandProcessing(Update update) {
        Long chatId = update.message().chat().id();
        String text = update.message().text();

        String resultMsg = commandHandlerService.handleCommand(chatId, text);

        sendMsg(chatId, resultMsg);
    }

    private void sendMsg(Long chatId, String msg) {
        SendMessage sendMessage = new SendMessage(chatId, msg);
        telegramBot.execute(sendMessage);
    }
}
