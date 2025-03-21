package com.example.portfolio.service.controller;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.dto.RetrieveWorkExperienceResponse;
import com.example.portfolio.service.dto.RetrieveWorkExperiencesResponse;
import com.example.portfolio.service.service.WorkExperienceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_SUCCESS;
import static com.example.portfolio.service.enums.LocationTypeEnum.ON_SITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkExperienceControllerTest {


    @InjectMocks
    private WorkExperienceController workExperienceController;

    @Mock
    private WorkExperienceService workExperienceService;

    private static final String JOB_TITLE = "Job Title";
    private static final String COMPANY = "Company";
    private static final LocalDate START_DATE = LocalDate.now().minusYears(5);
    private static final LocalDate END_DATE = LocalDate.now().minusYears(1);
    private static final String LOCATION = "Location";
    private static final String ACTIVITIES = "Some activities";
    private static final String DESCRIPTION = "Some description";
    private static final String SKILL = "Some skill";

    private static RetrieveWorkExperiencesResponse getMockWorkExperiences(List<RetrieveWorkExperienceResponse> educations) {
        return new RetrieveWorkExperiencesResponse(educations);
    }

    @Test
    void retrieveWorkExperiences_ReturnEmptyArray_Success() {
        RetrieveWorkExperiencesResponse mockWorkExperiences = getMockWorkExperiences(List.of());
        when(workExperienceService.retrieveWorkExperiences()).thenReturn(mockWorkExperiences);

        ResponsePayload<RetrieveWorkExperiencesResponse> actualResponse = workExperienceController.retrieveWorkExperiences();

        assertNotNull(actualResponse);
        assertEquals(STATUS_SUCCESS, actualResponse.getStatus());
        RetrieveWorkExperiencesResponse result = actualResponse.getResult();
        assertEquals(0, result.getWorkExperiences().size());
    }

    private static RetrieveWorkExperienceResponse buildMockWorkExperience() {
        RetrieveWorkExperienceResponse workExp = new RetrieveWorkExperienceResponse();
        workExp.setWorkExperienceId(1);
        workExp.setJobTitle(JOB_TITLE);
        workExp.setCompany(COMPANY);
        workExp.setStartDate(START_DATE);
        workExp.setEndDate(END_DATE);
        workExp.setLocation(LOCATION);
        workExp.setLocationType(ON_SITE.getValue());
        workExp.setCurrentlyWorkingFlag(Boolean.FALSE);
        workExp.setDescriptions(List.of(DESCRIPTION));
        workExp.setSkills(List.of(SKILL));
        return workExp;
    }

    @Test
    void retrieveWorkExperiences_ReturnWorkExperiences_Success() {
        RetrieveWorkExperiencesResponse mockWorkExperiences = getMockWorkExperiences(List.of(buildMockWorkExperience()));
        when(workExperienceService.retrieveWorkExperiences()).thenReturn(mockWorkExperiences);

        ResponsePayload<RetrieveWorkExperiencesResponse> actualResponse = workExperienceController.retrieveWorkExperiences();

        assertNotNull(actualResponse);
        assertEquals(STATUS_SUCCESS, actualResponse.getStatus());
        RetrieveWorkExperiencesResponse result = actualResponse.getResult();
        assertEquals(1, result.getWorkExperiences().size());

        RetrieveWorkExperienceResponse workExpResponse = result.getWorkExperiences().get(0);
        assertEquals(1, workExpResponse.getWorkExperienceId());
        assertEquals(JOB_TITLE, workExpResponse.getJobTitle());
        assertEquals(COMPANY, workExpResponse.getCompany());
        assertEquals(START_DATE, workExpResponse.getStartDate());
        assertEquals(END_DATE, workExpResponse.getEndDate());
        assertEquals(LOCATION, workExpResponse.getLocation());
        assertEquals(ON_SITE.getValue(), workExpResponse.getLocationType());
        assertEquals(Boolean.FALSE, workExpResponse.getCurrentlyWorkingFlag());

        assertEquals(1, workExpResponse.getDescriptions().size());
        assertEquals(DESCRIPTION, workExpResponse.getDescriptions().get(0));

        assertEquals(1, workExpResponse.getSkills().size());
        assertEquals(SKILL, workExpResponse.getSkills().get(0));
    }

}
