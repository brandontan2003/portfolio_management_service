package com.example.portfolio.service.controller;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.dto.RetrieveEducationResponse;
import com.example.portfolio.service.dto.RetrieveEducationsResponse;
import com.example.portfolio.service.service.EducationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EducationControllerTest {

    @InjectMocks
    private EducationController educationController;

    @Mock
    private EducationService educationService;

    private static final String SCHOOL = "School";
    private static final String DEGREE = "Degree";
    private static final String FIELD_OF_STUDIES = "Field of Studies";
    private static final LocalDate START_DATE = LocalDate.now().minusYears(5);
    private static final LocalDate END_DATE = LocalDate.now().minusYears(1);
    private static final String GRADE = "3.96";
    private static final String ACTIVITIES = "Some activities";
    private static final String DESCRIPTION = "Some description";
    private static final String SKILL = "Some skill";

    private static RetrieveEducationsResponse getMockEducations(List<RetrieveEducationResponse> educations) {
        return new RetrieveEducationsResponse(educations);
    }

    @Test
    void retrieveEducations_ReturnEmptyArray_Success() {
        RetrieveEducationsResponse mockEducations = getMockEducations(List.of());
        when(educationService.retrieveEducations()).thenReturn(mockEducations);

        ResponsePayload<RetrieveEducationsResponse> actualResponse = educationController.retrieveEducations();

        assertNotNull(actualResponse);
        assertEquals(STATUS_SUCCESS, actualResponse.getStatus());
        RetrieveEducationsResponse result = actualResponse.getResult();
        assertEquals(0, result.getEducations().size());
    }

    private static RetrieveEducationResponse buildMockEducation() {
        RetrieveEducationResponse education = new RetrieveEducationResponse();
        education.setEducationId(1);
        education.setSchool(SCHOOL);
        education.setDegree(DEGREE);
        education.setFieldOfStudies(FIELD_OF_STUDIES);
        education.setStartDate(START_DATE);
        education.setEndDate(END_DATE);
        education.setGrade(GRADE);
        education.setActivitiesAndSocieties(ACTIVITIES);
        education.setDescriptions(List.of(DESCRIPTION));
        education.setSkills(List.of(SKILL));
        return education;
    }

    @Test
    void retrieveEducations_ReturnEducations_Success() {
        RetrieveEducationsResponse mockEducations = getMockEducations(List.of(buildMockEducation()));
        when(educationService.retrieveEducations()).thenReturn(mockEducations);

        ResponsePayload<RetrieveEducationsResponse> actualResponse = educationController.retrieveEducations();

        assertNotNull(actualResponse);
        assertEquals(STATUS_SUCCESS, actualResponse.getStatus());
        RetrieveEducationsResponse result = actualResponse.getResult();
        assertEquals(1, result.getEducations().size());

        RetrieveEducationResponse educationResponse = result.getEducations().get(0);
        assertEquals(1, educationResponse.getEducationId());
        assertEquals(SCHOOL, educationResponse.getSchool());
        assertEquals(DEGREE, educationResponse.getDegree());
        assertEquals(FIELD_OF_STUDIES, educationResponse.getFieldOfStudies());
        assertEquals(START_DATE, educationResponse.getStartDate());
        assertEquals(END_DATE, educationResponse.getEndDate());
        assertEquals(GRADE, educationResponse.getGrade());
        assertEquals(ACTIVITIES, educationResponse.getActivitiesAndSocieties());

        assertEquals(1, educationResponse.getDescriptions().size());
        assertEquals(DESCRIPTION, educationResponse.getDescriptions().get(0));

        assertEquals(1, educationResponse.getSkills().size());
        assertEquals(SKILL, educationResponse.getSkills().get(0));
    }

}
