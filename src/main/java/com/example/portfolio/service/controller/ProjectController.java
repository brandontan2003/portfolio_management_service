package com.example.portfolio.service.controller;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.dto.RetrieveProjectsResponse;
import com.example.portfolio.service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_SUCCESS;
import static com.example.portfolio.service.constant.UriConstant.*;

@RestController
@RequestMapping(API_PORTFOLIO + PROJECT_URL)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(API_VERSION_1 + RETRIEVE_URL)
    public ResponsePayload<RetrieveProjectsResponse> retrieveProjects() {
        return ResponsePayload.<RetrieveProjectsResponse>builder().status(STATUS_SUCCESS)
                .result(projectService.retrieveProjects()).build();
    }

}
