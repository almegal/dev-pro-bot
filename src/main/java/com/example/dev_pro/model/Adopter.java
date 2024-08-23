package com.example.dev_pro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "adopter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"report"})
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy="adopter")
    private TelegramUser telegramUser;

    @OneToMany(mappedBy = "adopter")
    @JsonManagedReference
    private List<Pet> pet;

    @OneToMany(mappedBy = "adopter")
    @JsonManagedReference
    private List<Report> report;

}
