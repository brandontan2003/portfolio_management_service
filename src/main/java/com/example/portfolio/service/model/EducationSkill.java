package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.portfolio.service.constant.model.EducationModelConstant.EDUCATION_ID;
import static com.example.portfolio.service.constant.model.EducationSkillModelConstant.EDUCATION_SKILL_ID;
import static com.example.portfolio.service.constant.model.EducationSkillModelConstant.EDUCATION_SKILL_TABLE;
import static com.example.portfolio.service.constant.model.WorkExperienceSkillModelConstant.SKILLS;

@Data
@Entity
@Table(name = EDUCATION_SKILL_TABLE)
public class EducationSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EDUCATION_SKILL_ID, nullable = false)
    private Integer educationSkillId;
    @Column(name = EDUCATION_ID, nullable = false)
    private Integer educationId;
    @Column(name = SKILLS, nullable = false)
    private String skills;

    @ManyToOne
    @JoinColumn(name = EDUCATION_ID, nullable = false, insertable = false, updatable = false)
    private Education education;
}
