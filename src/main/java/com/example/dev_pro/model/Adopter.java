package com.example.dev_pro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "adopters")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"reports"})
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "telegram_user_id")
    private TelegramUser telegramUser;

    @OneToMany(mappedBy = "adopter", fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnore
    private List<Pet> pets;

    @OneToMany(mappedBy = "adopter", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Report> reports;

    private boolean passedTheProbationPeriod;

}

