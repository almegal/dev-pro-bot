package com.example.dev_pro.util;

import com.example.dev_pro.exception.InvalidInputException;
import com.example.dev_pro.model.TelegramUser;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    public static final Pattern pattern = Pattern.compile("([А-Яа-яЁё]+)(\\s)([А-Яа-яЁё]+)(\\s)([А-Яа-яЁё]+)(\\s)(\\d{10})(\\s)([А-Яа-яA-Za-z0-9]+)");

    /**
     * Распарсиваем строку, поступившую от пользователя в формате "Иванов Иван Иванович 9052341234 а123bc".
     * Первая, третья и пятая части паттерна описывают фамилию, имя и отчество: ([А-Яа-яЁё]+).
     * Седьмая часть паттерна описывает номер мобильного телефона: (\\d{10}).
     * Девятая часть паттерна описывает номер автомобиля: ([А-Яа-яA-Za-z0-9]+).
     * Четные части паттерна описывают пробел: (\\s).
     * Создаем объект типа Matcher для выполнения операций поиска по шаблону.
     * Проверяем, подходит ли строка под паттерн: matcher.find().
     */

    public static TelegramUser parseCreateCommand(TelegramUser telegramUser, String command) throws InvalidInputException {
        if (StringUtils.hasText(command)) {
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                telegramUser.setLastName(matcher.group(1));
                telegramUser.setFirstName(matcher.group(3));
                telegramUser.setMiddleName(matcher.group(5));
                telegramUser.setPhoneNumber(matcher.group(7));
                telegramUser.setCarNumber(matcher.group(9));
                return telegramUser;
            } else {
                System.err.println("Pattern not matched for command: " + command);
            }
        } else {
            System.err.println("Command is empty or null: " + command);
        }

        throw new InvalidInputException("Incorrect command: " + command);
    }
}