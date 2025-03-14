package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import static com.example.portfolio.service.constant.model.EducationModelConstant.*;

@Data
@Entity
@Table(name = EDUCATION_TABLE)
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EDUCATION_ID)
    private Integer educationId;
    @Column(name = SCHOOL, length = FieldLength.SCHOOL, nullable = false)
    private String school;
    @Column(name = DEGREE, length = FieldLength.DEGREE, nullable = false)
    private String degree;
    @Column(name = FIELD_OF_STUDIES, length = FieldLength.FIELD_OF_STUDIES, nullable = false)
    private String fieldOfStudies;
    @Column(name = START_DATE, nullable = false)
    private LocalDate startDate;
    @Column(name = END_DATE)
    private LocalDate endDate;
    @Column(name = GRADE)
    private String grade;
    @Column(name = ACTIVITIES_AND_SOCIETIES)
    private String activitiesAndSocieties;

    @OneToMany(mappedBy = EDUCATION_MAPPING, cascade = CascadeType.ALL)
    private List<EducationSkill> educationSkills;

    @OneToMany(mappedBy = EDUCATION_MAPPING, cascade = CascadeType.ALL)
    private List<EducationDescription> educationDescriptions;
}
