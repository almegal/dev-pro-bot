package com.example.dev_pro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
    private Adopter adopter;
    private String photoFilePath;

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }
}
