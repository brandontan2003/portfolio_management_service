package com.example.portfolio.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveProjectResponse {

    private Integer projectId;
    private String projectName;
    private String description;
    private String technologiesUsed;
    private String status;

    private List<String> githubLinks;
}
