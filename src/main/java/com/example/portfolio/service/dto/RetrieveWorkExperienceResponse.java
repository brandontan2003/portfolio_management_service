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
public class RetrieveWorkExperienceResponse {

    private Integer workExperienceId;
    private String jobTitle;
    private String company;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate endDate;
    private String location;
    private String locationType;
    private Boolean currentlyWorkingFlag;

    private List<String> skills;
    private List<String> descriptions;
}
