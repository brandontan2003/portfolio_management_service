package com.example.portfolio.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.example.portfolio.service.constant.DateTimeConstant.DATE_PATTERN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveEducationResponse {

    private Integer educationId;
    private String school;
    private String degree;
    private String fieldOfStudies;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate endDate;
    private String grade;
    private String activitiesAndSocieties;

    private List<String> skills;
    private List<String> descriptions;
}
