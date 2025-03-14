package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import static com.example.portfolio.service.constant.model.WorkExperienceModelConstant.*;

@Data
@Entity
@Table(name = WORK_EXPERIENCE_TABLE)
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = WORK_EXPERIENCE_ID)
    private Integer workExperienceId;
    @Column(name = JOB_TITLE, length = FieldLength.JOB_TITLE, nullable = false)
    private String jobTitle;
    @Column(name = COMPANY, length = FieldLength.COMPANY, nullable = false)
    private String company;
    @Column(name = START_DATE, nullable = false)
    private LocalDate startDate;
    @Column(name = END_DATE)
    private LocalDate endDate;
    @Column(name = LOCATION)
    private String location;
    @Column(name = LOCATION_TYPE, length = FieldLength.LOCATION_TYPE)
    private String locationType;
    @Column(name = CURRENTLY_WORKING_FLAG, nullable = false)
    private Boolean currentlyWorkingFlag;

    @OneToMany(mappedBy = WORK_EXPERIENCE_MAPPING, cascade = CascadeType.ALL)
    private List<WorkExperienceSkill> workExperienceSkills;

    @OneToMany(mappedBy = WORK_EXPERIENCE_MAPPING, cascade = CascadeType.ALL)
    private List<WorkExperienceDescription> workExperienceDescriptions;
}
