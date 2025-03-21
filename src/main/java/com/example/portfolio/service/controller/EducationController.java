package com.example.portfolio.service.controller;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.dto.RetrieveEducationsResponse;
import com.example.portfolio.service.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_SUCCESS;
import static com.example.portfolio.service.constant.UriConstant.*;

@RestController
@RequestMapping(API_PORTFOLIO + EDUCATION_URL)
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping(API_VERSION_1 + RETRIEVE_URL)
    public ResponsePayload<RetrieveEducationsResponse> retrieveEducations() {
        return ResponsePayload.<RetrieveEducationsResponse>builder().status(STATUS_SUCCESS)
                .result(educationService.retrieveEducations()).build();
    }

}
