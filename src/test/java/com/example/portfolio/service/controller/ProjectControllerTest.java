package com.example.portfolio.service.controller;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.dto.RetrieveProjectResponse;
import com.example.portfolio.service.dto.RetrieveProjectsResponse;
import com.example.portfolio.service.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_SUCCESS;
import static com.example.portfolio.service.enums.ProjectStatusEnum.COMPLETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    private static final String GITHUB_LINKS = "Github Links";
    private static final String DESCRIPTION = "Some description";
    private static final String TECHNOLOGIES_USED = "Technologies Used";
    private static final String PROJECT_NAME = "Project Name";

    private static RetrieveProjectsResponse getMockProjects(List<RetrieveProjectResponse> projects) {
        return new RetrieveProjectsResponse(projects);
    }

    @Test
    void retrieveProjects_ReturnEmptyArray_Success() {
        RetrieveProjectsResponse mockProject = getMockProjects(List.of());
        when(projectService.retrieveProjects()).thenReturn(mockProject);

        ResponsePayload<RetrieveProjectsResponse> actualResponse = projectController.retrieveProjects();

        assertNotNull(actualResponse);
        assertEquals(STATUS_SUCCESS, actualResponse.getStatus());
        RetrieveProjectsResponse result = actualResponse.getResult();
        assertEquals(0, result.getProjects().size());
    }

    private static RetrieveProjectResponse buildMockProject() {
        RetrieveProjectResponse project = new RetrieveProjectResponse();
        project.setProjectId(1);
        project.setProjectName(PROJECT_NAME);
        project.setStatus(COMPLETED.getValue());
        project.setTechnologiesUsed(TECHNOLOGIES_USED);
        project.setDescription(DESCRIPTION);
        project.setGithubLinks(List.of(GITHUB_LINKS));
        return project;
    }

    @Test
    void retrieveProjects_ReturnProjects_Success() {
        RetrieveProjectsResponse mockProject = getMockProjects(List.of(buildMockProject()));
        when(projectService.retrieveProjects()).thenReturn(mockProject);

        ResponsePayload<RetrieveProjectsResponse> actualResponse = projectController.retrieveProjects();

        assertNotNull(actualResponse);
        assertEquals(STATUS_SUCCESS, actualResponse.getStatus());
        RetrieveProjectsResponse result = actualResponse.getResult();
        assertEquals(1, result.getProjects().size());

        RetrieveProjectResponse projectResponse = result.getProjects().get(0);
        assertEquals(1, projectResponse.getProjectId());
        assertEquals(PROJECT_NAME, projectResponse.getProjectName());
        assertEquals(COMPLETED.getValue(), projectResponse.getStatus());
        assertEquals(TECHNOLOGIES_USED, projectResponse.getTechnologiesUsed());
        assertEquals(DESCRIPTION, projectResponse.getDescription());

        assertEquals(1, projectResponse.getGithubLinks().size());
        assertEquals(GITHUB_LINKS, projectResponse.getGithubLinks().get(0));
    }

}
