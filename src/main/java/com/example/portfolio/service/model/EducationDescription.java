package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.portfolio.service.constant.JpaConstant.MEDIUM_TEXT;
import static com.example.portfolio.service.constant.model.EducationDescriptionModelConstant.EDUCATION_DESCRIPTION_ID;
import static com.example.portfolio.service.constant.model.EducationDescriptionModelConstant.EDUCATION_DESCRIPTION_TABLE;
import static com.example.portfolio.service.constant.model.EducationModelConstant.EDUCATION_ID;
import static com.example.portfolio.service.constant.model.WorkExperienceDescriptionModelConstant.DESCRIPTION;

@Data
@Entity
@Table(name = EDUCATION_DESCRIPTION_TABLE)
public class EducationDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EDUCATION_DESCRIPTION_ID, nullable = false)
    private Integer educationDescriptionId;
    @Column(name = EDUCATION_ID, nullable = false)
    private Integer educationId;
    @Lob
    @Column(name = DESCRIPTION, columnDefinition = MEDIUM_TEXT, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = EDUCATION_ID, nullable = false, insertable = false, updatable = false)
    private Education education;
}
