package com.example.dev_pro.model;


import com.example.dev_pro.enums.PetType;
import com.example.dev_pro.enums.Sex;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "pets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"reports"})
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pet_type")
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "age")
    private Integer age;

    @Column(name = "free_status")
    private Boolean isFreeStatus;

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    @JsonBackReference
    @JsonIgnore
    private Adopter adopter;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    @JsonBackReference
    @JsonIgnore
    private Shelter shelter;

    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Report> reports;


}