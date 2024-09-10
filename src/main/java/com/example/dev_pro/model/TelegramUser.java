package com.example.dev_pro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telegram_users")
@NoArgsConstructor
@Data
public class TelegramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long telegramId;
    private Long chatId;
    private String nickName;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phoneNumber;
    private String carNumber;
    private String shelter;

    @OneToOne
    @JoinColumn(name = "adopter_id")
    @JsonIgnore
    private Adopter adopter;



    public TelegramUser(Long id, Long telegramId, Long chatId, String nickName, String lastName, String firstName,
                        String middleName, String phoneNumber, String carNumber, String shelter) {
        this.id = id;
        this.telegramId = telegramId;
        this.chatId = chatId;
        this.nickName = nickName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.carNumber = carNumber;
        this.shelter = shelter;
    }
}
