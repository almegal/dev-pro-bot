package com.example.dev_pro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    public Volunteer(Long id, Long chatId, String nickName, String lastName, String firstName, String middleName) {
        this.id = id;
        this.chatId = chatId;
        this.nickName = nickName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }
}
