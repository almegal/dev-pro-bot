package com.example.dev_pro.util;

import com.example.dev_pro.exception.InvalidInputException;
import com.example.dev_pro.model.TelegramUser;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    public static final Pattern pattern = Pattern.compile("([А-я]+)(\\s)([А-я]+)(\\s)([А-я]+)(\\s)" +
            "(\\d{10}$)");
    // (^((\+7)[-])(\d{3}\-)(\d{3}\-)(\d{2}\-)
    // (\s)([\W+]+)
    // (\s)([А-я0-9]+$)

    /**
     *
     * Распарсиваем строку, поступившую от пользователя в формате "Ivanov Ivan Ivanovich +7-905-234-12-34 а123bc cat shelter".
     * Первая часть паттерна описывает фамилию: ([А-я]+).
     * Третья часть паттерна описывает имя: ([А-я]+).
     * Пятая часть паттерна описывает отчество: ([А-я]+).
     * Седьмая часть паттерна описывает номер мобильного телефона: (^((\+7)-)(\d{3}-)(\d{3}-)(\d{2}-)(\d{2})$).
     * Девятая часть паттерна описывает номер автомобиля: (\s).
     * Одиннадцатая часть паттерна описывает тип приюта: ([\W+]+).
     * Четные части паттерна описывают пробел: (\s).
     * Создаем объект типа Matcher для выполнения операций поиска по шаблону.
     * Проверяем, подходит ли строка под паттерн: matcher.find().
     */

    public static TelegramUser parseCreateCommand(Long chatId, String userName, String command) throws InvalidInputException {
        if (StringUtils.hasText(command)) {
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                TelegramUser telegramUser = new TelegramUser();
                telegramUser.setTelegramId(chatId);
                telegramUser.setNickName(userName);
                telegramUser.setLastName(matcher.group(1));
                telegramUser.setFirstName(matcher.group(3));
                telegramUser.setMiddleName(matcher.group(5));
                telegramUser.setPhoneNumber(matcher.group(7));
                telegramUser.setCarNumber(matcher.group(9));
                telegramUser.setShelter(matcher.group(11));
                return new TelegramUser();
            }
        }

        throw new InvalidInputException("Incorrect command: " + command);
    }

}
