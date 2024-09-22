package com.example.dev_pro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_report")
    private LocalDate dateReport;

    @Column(name = "text_report")
    private String textReport;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "is_viewed")
    private Boolean isViewed;

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    //@JsonBackReference
    private Adopter adopter;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    //@JsonBackReference
    private Pet pet;


    public Report(Integer id, LocalDate dateReport, String textReport, String filePath, long fileSize, String mediaType,
                  Boolean isViewed) {
        this.id = id;
        this.dateReport = dateReport;
        this.textReport = textReport;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.isViewed = isViewed;
    }
}