package com.example.portfolio.service.service;

import com.example.portfolio.service.dto.RetrieveProjectResponse;
import com.example.portfolio.service.dto.RetrieveProjectsResponse;
import com.example.portfolio.service.model.Project;
import com.example.portfolio.service.model.ProjectSourceCode;
import com.example.portfolio.service.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper mapper;

    public RetrieveProjectsResponse retrieveProjects() {
        List<Project> projects = projectRepository.findAll();

        if (projects.isEmpty()) {
            return RetrieveProjectsResponse.builder().projects(Collections.emptyList()).build();
        }
        return RetrieveProjectsResponse.builder().projects(getProjectsResponse(projects)).build();
    }

    private static List<RetrieveProjectResponse> getProjectsResponse(List<Project> projects) {
        return projects.stream()
                .map(project -> RetrieveProjectResponse.builder()
                        .projectId(project.getProjectId())
                        .projectName(project.getProjectName())
                        .description(project.getDescription())
                        .technologiesUsed(project.getTechnologiesUsed())
                        .status(project.getStatus())
                        .githubLinks(Optional.ofNullable(project.getProjectSourceCodes())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(ProjectSourceCode::getGithubLink)
                                .toList())
                        .build())
                .toList();
    }

}
