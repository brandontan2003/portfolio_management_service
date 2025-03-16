package com.example.portfolio.service.service;

import com.example.portfolio.service.dto.RetrieveEducationResponse;
import com.example.portfolio.service.dto.RetrieveProjectResponse;
import com.example.portfolio.service.dto.RetrieveProjectsResponse;
import com.example.portfolio.service.model.*;
import com.example.portfolio.service.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.example.portfolio.service.enums.ProjectStatusEnum.COMPLETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    private static final String GITHUB_LINKS = "Github Links";
    private static final String DESCRIPTION = "Some description";

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private static Project getProject(String description, List<ProjectSourceCode> sourceCodes) {
        Project project = new Project();
        project.setProjectId(1);
        project.setProjectName("Project Name");
        project.setStatus(COMPLETED.getValue());
        project.setTechnologiesUsed("Technologies Used");
        project.setDescription(description);
        project.setProjectSourceCodes(sourceCodes);
        return project;
    }

    private static ProjectSourceCode getGithubLinks() {
        ProjectSourceCode sourceCode = new ProjectSourceCode();
        sourceCode.setProjectSourceCodeId(1);
        sourceCode.setProjectId(1);
        sourceCode.setGithubLink(GITHUB_LINKS);
        return sourceCode;
    }

    private static RetrieveProjectResponse getExpectedProjectResponse(String description, List<String> sourceCodes) {
        RetrieveProjectResponse project = new RetrieveProjectResponse();
        project.setProjectId(1);
        project.setProjectName("Project Name");
        project.setStatus(COMPLETED.getValue());
        project.setTechnologiesUsed("Technologies Used");
        project.setDescription(description);
        project.setGithubLinks(sourceCodes);
        return project;
    }

    static Stream<Arguments> test_retrieveProjects() {
        return Stream.of(
                Arguments.of("Test Retrieve Projects with mandatory fields only",
                        getProject(null, Collections.emptyList()),
                        getExpectedProjectResponse(null, Collections.emptyList())
                ),
                Arguments.of("Test Retrieve Projects where all the fields have values",
                        getProject(DESCRIPTION, List.of(getGithubLinks())),
                        getExpectedProjectResponse(DESCRIPTION, List.of(GITHUB_LINKS))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("test_retrieveProjects")
    void retrieveProjects_Success(String name, Project project, RetrieveProjectResponse buildExpectedResponse) {
        when(projectRepository.findAll()).thenReturn(List.of(project));

        RetrieveProjectsResponse actualResponse = projectService.retrieveProjects();
        RetrieveProjectsResponse expectedResponse =
                RetrieveProjectsResponse.builder().projects(List.of(buildExpectedResponse)).build();

        verify(projectRepository, times(1)).findAll();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getProjects(), actualResponse.getProjects());
        assertProjectResponse(actualResponse.getProjects().get(0), expectedResponse.getProjects().get(0));
    }

    private static void assertProjectResponse(RetrieveProjectResponse actualResponse, RetrieveProjectResponse expectedResponse) {
        assertEquals(expectedResponse.getProjectId(), actualResponse.getProjectId());
        assertEquals(expectedResponse.getProjectName(), actualResponse.getProjectName());
        assertEquals(expectedResponse.getDescription(), actualResponse.getDescription());
        assertEquals(expectedResponse.getTechnologiesUsed(), actualResponse.getTechnologiesUsed());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getGithubLinks(), actualResponse.getGithubLinks());
    }

    @Test
    void retrieveProjects_noRecordFound_Success() {
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());

        RetrieveProjectsResponse actualResponse = projectService.retrieveProjects();
        RetrieveProjectsResponse expectedResponse =
                RetrieveProjectsResponse.builder().projects(Collections.emptyList()).build();

        verify(projectRepository, times(1)).findAll();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getProjects(), actualResponse.getProjects());
    }
}
