package com.example.portfolio.service.service;

import com.example.portfolio.service.dto.RetrieveEducationResponse;
import com.example.portfolio.service.dto.RetrieveEducationsResponse;
import com.example.portfolio.service.model.Education;
import com.example.portfolio.service.model.EducationDescription;
import com.example.portfolio.service.model.EducationSkill;
import com.example.portfolio.service.repository.EducationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.portfolio.service.constant.JpaConstant.ORDER_BY_START_DATE;

@Service
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private ModelMapper mapper;

    public RetrieveEducationsResponse retrieveEducations() {
        List<Education> educations = educationRepository.findAll(Sort.by(Sort.Direction.DESC, ORDER_BY_START_DATE));
        if (educations.isEmpty()) {
            return RetrieveEducationsResponse.builder().educations(Collections.emptyList()).build();
        }

        return RetrieveEducationsResponse.builder().educations(getEducationsResponse(educations)).build();
    }

    private static List<RetrieveEducationResponse> getEducationsResponse(List<Education> educations) {
        return educations.stream().map(education -> RetrieveEducationResponse.builder()
                        .educationId(education.getEducationId())
                        .school(education.getSchool())
                        .degree(education.getDegree())
                        .fieldOfStudies(education.getFieldOfStudies())
                        .startDate(education.getStartDate())
                        .endDate(education.getEndDate())
                        .grade(education.getGrade())
                        .activitiesAndSocieties(education.getActivitiesAndSocieties())
                        .descriptions(getDescriptions(education))
                        .skills(getSkills(education))
                        .build())
                .toList();
    }

    private static List<String> getDescriptions(Education education) {
        return Optional.ofNullable(education.getEducationDescriptions())
                .orElse(Collections.emptyList())
                .stream()
                .map(EducationDescription::getDescription)
                .toList();
    }

    private static List<String> getSkills(Education education) {
        return Optional.ofNullable(education.getEducationSkills())
                .orElse(Collections.emptyList())
                .stream()
                .map(EducationSkill::getSkills)
                .toList();
    }
}
