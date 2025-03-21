package com.example.portfolio.service.service;

import com.example.portfolio.service.dto.RetrieveWorkExperienceResponse;
import com.example.portfolio.service.dto.RetrieveWorkExperiencesResponse;
import com.example.portfolio.service.enums.LocationTypeEnum;
import com.example.portfolio.service.model.WorkExperience;
import com.example.portfolio.service.model.WorkExperienceDescription;
import com.example.portfolio.service.model.WorkExperienceSkill;
import com.example.portfolio.service.repository.WorkExperienceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkExperienceServiceTest {
    private static final String DESCRIPTION = "Some description";
    private static final String SKILL = "Some skill";

    @Mock
    private WorkExperienceRepository workExperienceRepository;

    @InjectMocks
    private WorkExperienceService workExperienceService;

    private static WorkExperience getWorkExperience(LocalDate endDate, String location, String locationType,
                                                    List<WorkExperienceDescription> descriptions, List<WorkExperienceSkill> skills) {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setWorkExperienceId(1);
        workExperience.setJobTitle("Job Title");
        workExperience.setCompany("Company");
        workExperience.setStartDate(LocalDate.now().minusYears(5));
        workExperience.setEndDate(endDate);
        workExperience.setLocation(location);
        workExperience.setLocationType(locationType);
        workExperience.setCurrentlyWorkingFlag(Boolean.FALSE);
        workExperience.setWorkExperienceDescriptions(descriptions);
        workExperience.setWorkExperienceSkills(skills);
        return workExperience;
    }

    private static WorkExperienceDescription getDescription() {
        WorkExperienceDescription description = new WorkExperienceDescription();
        description.setWorkExperienceDescriptionId(1);
        description.setWorkExperienceId(1);
        description.setDescription(DESCRIPTION);
        return description;
    }

    private static WorkExperienceSkill getSkills() {
        WorkExperienceSkill skill = new WorkExperienceSkill();
        skill.setWorkExperienceSkillId(1);
        skill.setWorkExperienceId(1);
        skill.setSkills(SKILL);
        return skill;
    }

    private static RetrieveWorkExperienceResponse getExpectedWorkExpResponse(
            LocalDate endDate, String location, String locationType, List<String> descriptions,
            List<String> skills) {
        RetrieveWorkExperienceResponse workExp = new RetrieveWorkExperienceResponse();
        workExp.setWorkExperienceId(1);
        workExp.setJobTitle("Job Title");
        workExp.setCompany("Company");
        workExp.setStartDate(LocalDate.now().minusYears(5));
        workExp.setEndDate(endDate);
        workExp.setLocation(location);
        workExp.setLocationType(locationType);
        workExp.setCurrentlyWorkingFlag(Boolean.FALSE);
        workExp.setDescriptions(descriptions);
        workExp.setSkills(skills);
        return workExp;
    }

    static Stream<Arguments> test_retrieveWorkExperiences() {
        return Stream.of(
                Arguments.of("Test Retrieve Work Experiences with mandatory fields only",
                        getWorkExperience(null, null, null, Collections.emptyList(), Collections.emptyList()),
                        getExpectedWorkExpResponse(null, null, null, Collections.emptyList(), Collections.emptyList())
                ),
                Arguments.of("Test Retrieve Work Experiences where all the fields have values",
                        getWorkExperience(LocalDate.now(), "11", LocationTypeEnum.REMOTE.getValue(), List.of(getDescription()), List.of(getSkills())),
                        getExpectedWorkExpResponse(LocalDate.now(), "11", LocationTypeEnum.REMOTE.getValue(), List.of(DESCRIPTION), List.of(SKILL))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("test_retrieveWorkExperiences")
    void retrieveWorkExperiences_Success(String name, WorkExperience workExperience,
                                         RetrieveWorkExperienceResponse buildExpectedResponse) {
        when(workExperienceRepository.findAllOrderByStartDateDesc()).thenReturn(List.of(workExperience));

        RetrieveWorkExperiencesResponse actualResponse = workExperienceService.retrieveWorkExperiences();
        RetrieveWorkExperiencesResponse expectedResponse =
                RetrieveWorkExperiencesResponse.builder().workExperiences(List.of(buildExpectedResponse)).build();

        verify(workExperienceRepository, times(1)).findAllOrderByStartDateDesc();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getWorkExperiences(), actualResponse.getWorkExperiences());
        assertWorkExpResponse(actualResponse.getWorkExperiences().get(0), expectedResponse.getWorkExperiences().get(0));
    }

    private static void assertWorkExpResponse(RetrieveWorkExperienceResponse actualResponse,
                                                RetrieveWorkExperienceResponse expectedResponse) {
        assertEquals(expectedResponse.getWorkExperienceId(), actualResponse.getWorkExperienceId());
        assertEquals(expectedResponse.getJobTitle(), actualResponse.getJobTitle());
        assertEquals(expectedResponse.getCompany(), actualResponse.getCompany());
        assertEquals(expectedResponse.getStartDate(), actualResponse.getStartDate());
        assertEquals(expectedResponse.getEndDate(), actualResponse.getEndDate());
        assertEquals(expectedResponse.getLocation(), actualResponse.getLocation());
        assertEquals(expectedResponse.getLocationType(), actualResponse.getLocationType());
        assertEquals(expectedResponse.getCurrentlyWorkingFlag(), actualResponse.getCurrentlyWorkingFlag());
        assertEquals(expectedResponse.getDescriptions(), actualResponse.getDescriptions());
        assertEquals(expectedResponse.getSkills(), actualResponse.getSkills());
    }

    @Test
    void retrieveWorkExperiences_noRecordFound_Success() {
        when(workExperienceRepository.findAllOrderByStartDateDesc()).thenReturn(Collections.emptyList());

        RetrieveWorkExperiencesResponse actualResponse = workExperienceService.retrieveWorkExperiences();
        RetrieveWorkExperiencesResponse expectedResponse =
                RetrieveWorkExperiencesResponse.builder().workExperiences(Collections.emptyList()).build();

        verify(workExperienceRepository, times(1)).findAllOrderByStartDateDesc();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getWorkExperiences(), actualResponse.getWorkExperiences());
    }
}
