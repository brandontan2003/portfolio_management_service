package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.portfolio.service.constant.JpaConstant.MEDIUM_TEXT;
import static com.example.portfolio.service.constant.model.WorkExperienceDescriptionModelConstant.*;
import static com.example.portfolio.service.constant.model.WorkExperienceModelConstant.WORK_EXPERIENCE_ID;

@Data
@Entity
@Table(name = WORK_EXPERIENCE_DESCRIPTION_TABLE)
public class WorkExperienceDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = WORK_EXPERIENCE_DESCRIPTION_ID, nullable = false)
    private Integer workExperienceDescriptionId;
    @Column(name = WORK_EXPERIENCE_ID, nullable = false)
    private Integer workExperienceId;
    @Lob
    @Column(name = DESCRIPTION, columnDefinition = MEDIUM_TEXT, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = WORK_EXPERIENCE_ID, nullable = false, insertable = false, updatable = false)
    private WorkExperience workExperience;
}
