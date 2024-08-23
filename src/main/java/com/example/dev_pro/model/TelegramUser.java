package com.example.dev_pro.model;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.botapi.BotStateDogShelter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@NoArgsConstructor
@Data
public class TelegramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_id")
    private Long telegramId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "shelter")
    private String shelter;

    @Column(name = "photo_file_path")
    private String photoFilePath;

    @Enumerated(EnumType.STRING)
    private BotStateCatShelter botState;

    @Enumerated(EnumType.STRING)
    private BotStateDogShelter botStateDogShelter;

    @OneToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

}