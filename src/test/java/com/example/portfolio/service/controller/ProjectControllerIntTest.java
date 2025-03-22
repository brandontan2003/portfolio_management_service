package com.example.portfolio.service.controller;

import com.example.portfolio.service.model.Project;
import com.example.portfolio.service.model.ProjectSourceCode;
import com.example.portfolio.service.repository.ProjectRepository;
import com.example.portfolio.service.repository.ProjectSourceCodeRepository;
import com.example.portfolio.service.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static com.example.portfolio.service.TestUtils.*;
import static com.example.portfolio.service.constant.UriConstant.*;
import static com.example.portfolio.service.enums.ProjectStatusEnum.COMPLETED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProjectController projectController;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectSourceCodeRepository projectSourceCodeRepository;

    private static final String TECHNOLOGIES_USED = "Technologies Used";
    private static final String PROJECT_NAME = "Project Name";
    private static final String GITHUB_LINKS = "Github Links";
    private static final String DESCRIPTION = "Some description";
    private static final Path basePath = Paths.get("src", "test", "resources", "expected_output", "project");

    @BeforeEach
    void setUp() {
        projectSourceCodeRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @AfterEach
    void teardown() {
        projectSourceCodeRepository.deleteAll();
        projectRepository.deleteAll();
    }

    private Project saveProject(String description) {
        Project project = new Project();
        project.setProjectName(PROJECT_NAME);
        project.setStatus(COMPLETED.getValue());
        project.setTechnologiesUsed(TECHNOLOGIES_USED);
        project.setDescription(description);
        return projectRepository.saveAndFlush(project);
    }

    private void saveProjectSourceCode(Project project, String githubLinks) {
        ProjectSourceCode sourceCode = new ProjectSourceCode();
        sourceCode.setProjectId(project.getProjectId());
        sourceCode.setGithubLink(githubLinks);
        projectSourceCodeRepository.saveAndFlush(sourceCode);
    }

    static Stream<Arguments> test_retrieveProjects_Success() {
        return Stream.of(
                Arguments.of("Retrieve projects where there is no description", null, GITHUB_LINKS,
                        basePath.resolve("retrieveProjects_noDescription_success.json")),
                Arguments.of("Retrieve projects where there is no Github Links", DESCRIPTION, null,
                        basePath.resolve("retrieveProjects_noLinks_success.json")),
                Arguments.of("Retrieve projects for nullable fields", null, null,
                        basePath.resolve("retrieveProjects_nullableFields_success.json")),
                Arguments.of("Retrieve projects where all fields contains value", DESCRIPTION, GITHUB_LINKS,
                        basePath.resolve("retrieveProjects_success.json"))
        );
    }

    @ParameterizedTest
    @MethodSource("test_retrieveProjects_Success")
    void retrieveProjects_Success(String name, String description, String sourceCode, Path outputFile) throws Exception {
        Project project = saveProject(description);
        if (StringUtils.isNotBlank(sourceCode)) {
            saveProjectSourceCode(project, sourceCode);
        }
        String actualResponse = mvc.perform(get(API_PORTFOLIO + PROJECT_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(outputFile);
        expectedResponse = expectedResponse.replace("\"#projectId#\"", project.getProjectId().toString());
        log.info(EXPECTED_RESPONSE + expectedResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    void retrieveProjects_multipleRecords_Success() throws Exception {
        Project project1 = saveProject(DESCRIPTION);
        saveProjectSourceCode(project1, GITHUB_LINKS);
        Project project2 = saveProject(null);
        saveProjectSourceCode(project2, GITHUB_LINKS);
        Project project3 = saveProject(DESCRIPTION);

        String actualResponse = mvc.perform(get(API_PORTFOLIO + PROJECT_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(basePath.resolve("retrieveProjects_multipleRecords_success.json"));
        expectedResponse = expectedResponse.replace("\"#projectId1#\"", project1.getProjectId().toString());
        expectedResponse = expectedResponse.replace("\"#projectId2#\"", project2.getProjectId().toString());
        expectedResponse = expectedResponse.replace("\"#projectId3#\"", project3.getProjectId().toString());
        log.info(EXPECTED_RESPONSE + expectedResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    void retrieveProjects_noRecords_Success() throws Exception {
        String actualResponse = mvc.perform(get(API_PORTFOLIO + PROJECT_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(basePath.resolve("retrieveProjects_noRecords_success.json"));
        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }
}
