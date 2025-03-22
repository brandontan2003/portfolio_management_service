package com.example.portfolio.service.controller;

import com.example.portfolio.service.model.*;
import com.example.portfolio.service.repository.*;
import com.example.portfolio.service.service.WorkExperienceService;
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
import java.time.LocalDate;
import java.util.stream.Stream;

import static com.example.portfolio.service.TestUtils.*;
import static com.example.portfolio.service.constant.UriConstant.*;
import static com.example.portfolio.service.enums.LocationTypeEnum.HYBRID;
import static com.example.portfolio.service.enums.LocationTypeEnum.REMOTE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class WorkExperienceControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WorkExperienceController workExperienceController;

    @Autowired
    private WorkExperienceService workExperienceService;

    @Autowired
    private WorkExperienceRepository workExperienceRepository;
    @Autowired
    private WorkExperienceSkillRepository workExperienceSkillRepository;
    @Autowired
    private WorkExperienceDescriptionRepository workExperienceDescriptionRepository;

    private static final String SOME_ACTIVITIES = "Some activities";
    private static final String LOCATION = "Singapore";
    private static final String SKILL = "Some skill";
    private static final String DESCRIPTION = "Some description";
    private static final Path basePath = Paths.get("src", "test", "resources", "expected_output", "work_exp");

    @BeforeEach
    void setUp() {
        workExperienceDescriptionRepository.deleteAll();
        workExperienceSkillRepository.deleteAll();
        workExperienceRepository.deleteAll();
    }

    @AfterEach
    void teardown() {
        workExperienceDescriptionRepository.deleteAll();
        workExperienceSkillRepository.deleteAll();
        workExperienceRepository.deleteAll();
    }

    private WorkExperience saveWorkExp(LocalDate endDate, String location, String locationType) {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setJobTitle("Job Title");
        workExperience.setCompany("Company");
        workExperience.setStartDate(LocalDate.of(2020, 4, 19));
        workExperience.setEndDate(endDate);
        workExperience.setLocation(location);
        workExperience.setLocationType(locationType);
        workExperience.setCurrentlyWorkingFlag(Boolean.FALSE);
        return workExperienceRepository.saveAndFlush(workExperience);
    }

    private void saveWorkExpDescription(WorkExperience workExperience, String description) {
        WorkExperienceDescription workExpDescription = new WorkExperienceDescription();
        workExpDescription.setWorkExperienceId(workExperience.getWorkExperienceId());
        workExpDescription.setDescription(description);
        workExperienceDescriptionRepository.saveAndFlush(workExpDescription);
    }

    private void saveWorkExpSkill(WorkExperience workExperience, String skill) {
        WorkExperienceSkill workExpSkill = new WorkExperienceSkill();
        workExpSkill.setWorkExperienceId(workExperience.getWorkExperienceId());
        workExpSkill.setSkills(skill);
        workExperienceSkillRepository.saveAndFlush(workExpSkill);
    }

    static Stream<Arguments> test_retrieveWorkExperiences_Success() {
        return Stream.of(
                Arguments.of("Retrieve educations for nullable fields", null, null, null, null, null,
                        basePath.resolve("retrieveWorkExp_nullableFields_success.json")),
                Arguments.of("Retrieve educations for  fields", LocalDate.of(2023, 3, 20),
                        LOCATION, REMOTE.getValue(), DESCRIPTION, SKILL,
                        basePath.resolve("retrieveWorkExp_success.json"))
        );
    }

    @ParameterizedTest
    @MethodSource("test_retrieveWorkExperiences_Success")
    void retrieveWorkExperiences_Success(String name, LocalDate endDate, String location, String locationType,
                                    String description, String skill, Path outputFile) throws Exception {
        WorkExperience workExp = saveWorkExp(endDate, location, locationType);
        if (StringUtils.isNotBlank(description)) {
            saveWorkExpDescription(workExp, description);
        }
        if (StringUtils.isNotBlank(skill)) {
            saveWorkExpSkill(workExp, skill);
        }

        String actualResponse = mvc.perform(get(API_PORTFOLIO + WORK_EXPERIENCE_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(outputFile);
        expectedResponse = expectedResponse.replace("\"#workExperienceId#\"", workExp.getWorkExperienceId().toString());
        log.info(EXPECTED_RESPONSE + expectedResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    void retrieveWorkExperiences_multipleRecords_Success() throws Exception {
        WorkExperience workExp1 = saveWorkExp(LocalDate.of(2023, 3, 20), null, null);
        saveWorkExpSkill(workExp1, SKILL);
        WorkExperience workExp2 = saveWorkExp(null, LOCATION, HYBRID.getValue());
        saveWorkExpDescription(workExp2, DESCRIPTION);

        String actualResponse = mvc.perform(get(API_PORTFOLIO + WORK_EXPERIENCE_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(basePath.resolve("retrieveWorkExp_multipleRecords_success.json"));
        expectedResponse = expectedResponse.replace("\"#workExperienceId1#\"", workExp1.getWorkExperienceId().toString());
        expectedResponse = expectedResponse.replace("\"#workExperienceId2#\"", workExp2.getWorkExperienceId().toString());
        log.info(EXPECTED_RESPONSE + expectedResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    void retrieveWorkExperiences_noRecords_Success() throws Exception {
        String actualResponse = mvc.perform(get(API_PORTFOLIO + WORK_EXPERIENCE_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(basePath.resolve("retrieveWorkExp_noRecords_success.json"));
        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }
}
