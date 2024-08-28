package com.example.dev_pro.model;


import com.example.dev_pro.enums.ShelterType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "shelters")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"pets"})
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "shelter_type")
    @Enumerated(EnumType.STRING)
    private ShelterType shelterType;

    @Column(name = "address")
    private String address;

    @Column(name = "time_work")
    private String timeWork;

    @Column(name = "phone_shelter")
    private String phoneShelter;

    @Column(name = "phone_security")
    private String phoneSecurity;

    @OneToMany(mappedBy = "shelter")
    @JsonManagedReference
    private List<Pet> pets;
}
