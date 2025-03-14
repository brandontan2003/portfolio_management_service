package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.portfolio.service.constant.model.WorkExperienceModelConstant.WORK_EXPERIENCE_ID;
import static com.example.portfolio.service.constant.model.WorkExperienceSkillModelConstant.*;

@Data
@Entity
@Table(name = WORK_EXPERIENCE_SKILL_TABLE)
public class WorkExperienceSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = WORK_EXPERIENCE_SKILL_ID, nullable = false)
    private Integer workExperienceSkillId;
    @Column(name = WORK_EXPERIENCE_ID, nullable = false)
    private Integer workExperienceId;
    @Column(name = SKILLS, nullable = false)
    private String skills;

    @ManyToOne
    @JoinColumn(name = WORK_EXPERIENCE_ID, nullable = false, insertable = false, updatable = false)
    private WorkExperience workExperience;
}
