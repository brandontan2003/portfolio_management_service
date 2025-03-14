package com.example.portfolio.service.controller;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.dto.RetrieveHomepageContentResponse;
import com.example.portfolio.service.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_SUCCESS;
import static com.example.portfolio.service.constant.UriConstant.*;

@RestController
@RequestMapping(API_PORTFOLIO)
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping(API_VERSION_1 + RETRIEVE_URL + SECTION_PATH_VARIABLE)
    public ResponsePayload<RetrieveHomepageContentResponse> retrieveContent(@PathVariable String section) {
        return ResponsePayload.<RetrieveHomepageContentResponse>builder().status(STATUS_SUCCESS)
                .result(portfolioService.retrieveContent(section)).build();
    }

}
