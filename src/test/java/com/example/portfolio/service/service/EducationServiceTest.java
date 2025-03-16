package com.example.portfolio.service.service;

import com.example.portfolio.service.dto.RetrieveEducationResponse;
import com.example.portfolio.service.dto.RetrieveEducationsResponse;
import com.example.portfolio.service.model.Education;
import com.example.portfolio.service.model.EducationDescription;
import com.example.portfolio.service.model.EducationSkill;
import com.example.portfolio.service.repository.EducationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.example.portfolio.service.constant.JpaConstant.ORDER_BY_START_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EducationServiceTest {
    private static final String DESCRIPTION = "Some description";
    private static final String SKILL = "Some skill";

    @Mock
    private EducationRepository educationRepository;

    @InjectMocks
    private EducationService educationService;

    private static Education getEducation(LocalDate endDate, String grade, String activities,
                                          List<EducationDescription> descriptions, List<EducationSkill> skills) {
        Education education = new Education();
        education.setEducationId(1);
        education.setSchool("School");
        education.setDegree("Degree");
        education.setFieldOfStudies("Field of Studies");
        education.setStartDate(LocalDate.now().minusYears(5));
        education.setEndDate(endDate);
        education.setGrade(grade);
        education.setActivitiesAndSocieties(activities);
        education.setEducationDescriptions(descriptions);
        education.setEducationSkills(skills);
        return education;
    }

    private static EducationDescription getDescription() {
        EducationDescription description = new EducationDescription();
        description.setEducationDescriptionId(1);
        description.setEducationId(1);
        description.setDescription(DESCRIPTION);
        return description;
    }

    private static EducationSkill getSkills() {
        EducationSkill skill = new EducationSkill();
        skill.setEducationSkillId(1);
        skill.setEducationId(1);
        skill.setSkills(SKILL);
        return skill;
    }

    private static RetrieveEducationResponse getExpectedEducationResponse(
            LocalDate endDate, String grade, String activities, List<String> descriptions,
            List<String> skills) {
        RetrieveEducationResponse education = new RetrieveEducationResponse();
        education.setEducationId(1);
        education.setSchool("School");
        education.setDegree("Degree");
        education.setFieldOfStudies("Field of Studies");
        education.setStartDate(LocalDate.now().minusYears(5));
        education.setEndDate(endDate);
        education.setGrade(grade);
        education.setActivitiesAndSocieties(activities);
        education.setDescriptions(descriptions);
        education.setSkills(skills);
        return education;
    }

    static Stream<Arguments> test_retrieveEducations() {
        return Stream.of(
                Arguments.of("Test Retrieve Educations with mandatory fields only",
                        getEducation(null, null, null, Collections.emptyList(), Collections.emptyList()),
                        getExpectedEducationResponse(null, null, null, Collections.emptyList(), Collections.emptyList())
                ),
                Arguments.of("Test Retrieve Educations where all the fields have values",
                        getEducation(LocalDate.now(), "11", "activities", List.of(getDescription()), List.of(getSkills())),
                        getExpectedEducationResponse(LocalDate.now(), "11", "activities", List.of(DESCRIPTION), List.of(SKILL))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("test_retrieveEducations")
    void retrieveEducations_Success(String name, Education education, RetrieveEducationResponse buildExpectedResponse) {
        when(educationRepository.findAll(Sort.by(Sort.Direction.DESC, ORDER_BY_START_DATE))).thenReturn(List.of(education));

        RetrieveEducationsResponse actualResponse = educationService.retrieveEducations();
        RetrieveEducationsResponse expectedResponse =
                RetrieveEducationsResponse.builder().educations(List.of(buildExpectedResponse)).build();

        verify(educationRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, ORDER_BY_START_DATE));
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getEducations(), actualResponse.getEducations());
        assertEducationResponse(actualResponse.getEducations().get(0), expectedResponse.getEducations().get(0));
    }

    private static void assertEducationResponse(RetrieveEducationResponse actualResponse, RetrieveEducationResponse expectedResponse) {
        assertEquals(expectedResponse.getEducationId(), actualResponse.getEducationId());
        assertEquals(expectedResponse.getSchool(), actualResponse.getSchool());
        assertEquals(expectedResponse.getDegree(), actualResponse.getDegree());
        assertEquals(expectedResponse.getFieldOfStudies(), actualResponse.getFieldOfStudies());
        assertEquals(expectedResponse.getStartDate(), actualResponse.getStartDate());
        assertEquals(expectedResponse.getEndDate(), actualResponse.getEndDate());
        assertEquals(expectedResponse.getGrade(), actualResponse.getGrade());
        assertEquals(expectedResponse.getActivitiesAndSocieties(), actualResponse.getActivitiesAndSocieties());
        assertEquals(expectedResponse.getDescriptions(), actualResponse.getDescriptions());
        assertEquals(expectedResponse.getSkills(), actualResponse.getSkills());
    }

    @Test
    void retrieveEducations_noRecordFound_Success() {
        when(educationRepository.findAll(Sort.by(Sort.Direction.DESC, ORDER_BY_START_DATE))).thenReturn(Collections.emptyList());

        RetrieveEducationsResponse actualResponse = educationService.retrieveEducations();
        RetrieveEducationsResponse expectedResponse =
                RetrieveEducationsResponse.builder().educations(Collections.emptyList()).build();

        verify(educationRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, ORDER_BY_START_DATE));
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getEducations(), actualResponse.getEducations());
    }
}
