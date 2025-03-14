package com.example.portfolio.service.controller;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.dto.RetrieveWorkExperiencesResponse;
import com.example.portfolio.service.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_SUCCESS;
import static com.example.portfolio.service.constant.UriConstant.*;

@RestController
@RequestMapping(API_PORTFOLIO + WORK_EXPERIENCE_URL)
public class WorkExperienceController {

    @Autowired
    private WorkExperienceService workExperienceService;

    @GetMapping(API_VERSION_1 + RETRIEVE_URL)
    public ResponsePayload<RetrieveWorkExperiencesResponse> retrieveWorkExperiences() {
        return ResponsePayload.<RetrieveWorkExperiencesResponse>builder().status(STATUS_SUCCESS)
                .result(workExperienceService.retrieveWorkExperiences()).build();
    }

}
