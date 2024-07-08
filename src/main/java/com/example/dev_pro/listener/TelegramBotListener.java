package com.example.dev_pro.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotListener implements UpdatesListener {
    @Autowired
    TelegramBot bot;

    @PostConstruct
    public void init() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        list.forEach(update -> {
            Long chatId = update.message().chat().id();
            // вызвать метод слоя для обработки комманд


            //убрать  заглушку
            SendMessage msg = new SendMessage(chatId, ":)");
            bot.execute(msg);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
