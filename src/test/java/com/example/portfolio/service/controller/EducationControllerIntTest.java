package com.example.portfolio.service.controller;

import com.example.portfolio.service.model.Education;
import com.example.portfolio.service.model.EducationDescription;
import com.example.portfolio.service.model.EducationSkill;
import com.example.portfolio.service.model.Project;
import com.example.portfolio.service.repository.EducationDescriptionRepository;
import com.example.portfolio.service.repository.EducationRepository;
import com.example.portfolio.service.repository.EducationSkillRepository;
import com.example.portfolio.service.service.EducationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class EducationControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EducationController educationController;

    @Autowired
    private EducationService educationService;

    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private EducationSkillRepository educationSkillRepository;
    @Autowired
    private EducationDescriptionRepository educationDescriptionRepository;

    private static final String SOME_ACTIVITIES = "Some activities";
    private static final String GRADE = "3.96";
    private static final String SKILL = "Some skill";
    private static final String DESCRIPTION = "Some description";
    private static final Path basePath = Paths.get("src", "test", "resources", "expected_output", "education");

    @BeforeEach
    void setUp() {
        educationDescriptionRepository.deleteAll();
        educationSkillRepository.deleteAll();
        educationRepository.deleteAll();
    }

    @AfterEach
    void teardown() {
        educationDescriptionRepository.deleteAll();
        educationSkillRepository.deleteAll();
        educationRepository.deleteAll();
    }

    private Education saveEducation(LocalDate endDate, String grade, String activities) {
        Education education = new Education();
        education.setSchool("School");
        education.setDegree("Degree");
        education.setFieldOfStudies("Field of Studies");
        education.setStartDate(LocalDate.of(2020, 4, 19));
        education.setEndDate(endDate);
        education.setGrade(grade);
        education.setActivitiesAndSocieties(activities);
        return educationRepository.saveAndFlush(education);
    }

    private void saveEducationDescription(Education education, String description) {
        EducationDescription educationDescription = new EducationDescription();
        educationDescription.setEducationId(education.getEducationId());
        educationDescription.setDescription(description);
        educationDescriptionRepository.saveAndFlush(educationDescription);
    }

    private void saveEducationSkill(Education education, String skill) {
        EducationSkill educationSkill = new EducationSkill();
        educationSkill.setEducationId(education.getEducationId());
        educationSkill.setSkills(skill);
        educationSkillRepository.saveAndFlush(educationSkill);
    }

    static Stream<Arguments> test_retrieveEducations_Success() {
        return Stream.of(
                Arguments.of("Retrieve educations for nullable fields", null, null, null, null, null,
                        basePath.resolve("retrieveEducations_nullableFields_success.json")),
                Arguments.of("Retrieve educations for  fields", LocalDate.of(2023, 3, 20),
                        GRADE, SOME_ACTIVITIES, DESCRIPTION, SKILL,
                        basePath.resolve("retrieveEducations_success.json"))
        );
    }

    @ParameterizedTest
    @MethodSource("test_retrieveEducations_Success")
    void retrieveEducations_Success(String name, LocalDate endDate, String grade, String activities,
                                    String description, String skill, Path outputFile) throws Exception {
        Education education = saveEducation(endDate, grade, activities);
        if (StringUtils.isNotBlank(description)) {
            saveEducationDescription(education, description);
        }
        if (StringUtils.isNotBlank(skill)) {
            saveEducationSkill(education, skill);
        }

        String actualResponse = mvc.perform(get(API_PORTFOLIO + EDUCATION_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(outputFile);
        expectedResponse = expectedResponse.replace("\"#educationId#\"", education.getEducationId().toString());
        log.info(EXPECTED_RESPONSE + expectedResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    void retrieveEducations_multipleRecords_Success() throws Exception {
        Education education1 = saveEducation(LocalDate.of(2023, 3, 20), null, null);
        saveEducationSkill(education1, SKILL);
        Education education2 = saveEducation(null, GRADE, SOME_ACTIVITIES);
        saveEducationDescription(education2, DESCRIPTION);

        String actualResponse = mvc.perform(get(API_PORTFOLIO + EDUCATION_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(basePath.resolve("retrieveEducations_multipleRecords_success.json"));
        expectedResponse = expectedResponse.replace("\"#educationId1#\"", education1.getEducationId().toString());
        expectedResponse = expectedResponse.replace("\"#educationId2#\"", education2.getEducationId().toString());
        log.info(EXPECTED_RESPONSE + expectedResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    void retrieveEducations_noRecords_Success() throws Exception {
        String actualResponse = mvc.perform(get(API_PORTFOLIO + EDUCATION_URL + API_VERSION_1 + RETRIEVE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(basePath.resolve("retrieveEducations_noRecords_success.json"));
        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }
}
