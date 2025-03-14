package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.portfolio.service.constant.model.ProjectModelConstant.PROJECT_ID;
import static com.example.portfolio.service.constant.model.ProjectSourceCodeModelConstant.*;


@Data
@Entity
@Table(name = PROJECT_SOURCE_CODE_TABLE)
public class ProjectSourceCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PROJECT_SOURCE_CODE_ID, nullable = false)
    private Integer projectSourceCodeId;
    @Column(name = PROJECT_ID, nullable = false)
    private Integer projectId;
    @Column(name = GITHUB_LINK, nullable = false)
    private String githubLink;

    @ManyToOne
    @JoinColumn(name = PROJECT_ID, nullable = false, insertable = false, updatable = false)
    private Project project;
}
