package com.example.portfolio.service.service;

import com.example.portfolio.service.dto.RetrieveWorkExperienceResponse;
import com.example.portfolio.service.dto.RetrieveWorkExperiencesResponse;
import com.example.portfolio.service.model.WorkExperience;
import com.example.portfolio.service.model.WorkExperienceDescription;
import com.example.portfolio.service.model.WorkExperienceSkill;
import com.example.portfolio.service.repository.WorkExperienceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WorkExperienceService {

    @Autowired
    private WorkExperienceRepository workExperienceRepository;
    @Autowired
    private ModelMapper mapper;

    public RetrieveWorkExperiencesResponse retrieveWorkExperiences() {
        List<WorkExperience> workExperiences = workExperienceRepository.findAllOrderByStartDateDesc();
        if (workExperiences.isEmpty()) {
            return RetrieveWorkExperiencesResponse.builder().workExperiences(Collections.emptyList()).build();
        }

        return RetrieveWorkExperiencesResponse.builder()
                .workExperiences(getWorkExperiencesResponse(workExperiences)).build();
    }

    private static List<RetrieveWorkExperienceResponse> getWorkExperiencesResponse(List<WorkExperience> workExperiences) {
        return workExperiences.stream().map(workExperience -> RetrieveWorkExperienceResponse.builder()
                        .workExperienceId(workExperience.getWorkExperienceId())
                        .jobTitle(workExperience.getJobTitle())
                        .company(workExperience.getCompany())
                        .startDate(workExperience.getStartDate())
                        .endDate(workExperience.getEndDate())
                        .location(workExperience.getLocation())
                        .locationType(workExperience.getLocationType())
                        .currentlyWorkingFlag(workExperience.getCurrentlyWorkingFlag())
                        .descriptions(getDescriptions(workExperience))
                        .skills(getSkills(workExperience))
                        .build())
                .toList();
    }

    private static List<String> getDescriptions(WorkExperience workExperience) {
        return Optional.ofNullable(workExperience.getWorkExperienceDescriptions())
                .orElse(Collections.emptyList())
                .stream()
                .map(WorkExperienceDescription::getDescription)
                .toList();
    }

    private static List<String> getSkills(WorkExperience workExperience) {
        return Optional.ofNullable(workExperience.getWorkExperienceSkills())
                .orElse(Collections.emptyList())
                .stream()
                .map(WorkExperienceSkill::getSkills)
                .toList();
    }
}
