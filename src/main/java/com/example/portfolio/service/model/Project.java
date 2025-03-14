package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import static com.example.portfolio.service.constant.JpaConstant.TEXT;
import static com.example.portfolio.service.constant.model.ProjectModelConstant.*;

@Data
@Entity
@Table(name = PROJECT_TABLE)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PROJECT_ID, nullable = false)
    private Integer projectId;
    @Column(name = PROJECT_NAME, nullable = false)
    private String projectName;
    @Lob
    @Column(name = DESCRIPTION, columnDefinition = TEXT)
    private String description;
    @Column(name = TECHNOLOGIES_USED, length = FieldLength.TECHNOLOGIES_USED, nullable = false)
    private String technologiesUsed;
    @Column(name = STATUS, length = FieldLength.STATUS, nullable = false)
    private String status;

    @OneToMany(mappedBy = PROJECT_MAPPING, cascade = CascadeType.ALL)
    private List<ProjectSourceCode> projectSourceCodes;
}
