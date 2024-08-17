package com.example.dev_pro.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long report_id;

    @Column(name = "nameAnimal")
    private String nameAnimal;

    @Column(name = "mode")
    private String mode;

    @Column(name = "ration")
    private String ration;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "wellBeing")
    private String wellBeing;

    @OneToMany(mappedBy = "report")
    private List<PhotoReport> photoReport;

    public Long getReport_id() {
        return report_id;
    }

    public void setReport_id(Long report_id) {
        this.report_id = report_id;
    }

    public String getNameAnimal() {
        return nameAnimal;
    }

    public void setNameAnimal(String nameAnimal) {
        this.nameAnimal = nameAnimal;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getWellBeing() {
        return wellBeing;
    }

    public void setWellBeing(String wellBeing) {
        this.wellBeing = wellBeing;
    }


}
