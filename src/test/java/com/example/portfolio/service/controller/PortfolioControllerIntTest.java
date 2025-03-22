package com.example.portfolio.service.controller;

import com.example.portfolio.service.model.HomepageContent;
import com.example.portfolio.service.repository.HomepageContentRepository;
import com.example.portfolio.service.service.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.example.portfolio.service.TestUtils.*;
import static com.example.portfolio.service.constant.UriConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class PortfolioControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PortfolioController portfolioController;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private HomepageContentRepository homepageContentRepository;

    private static final String HOMEPAGE_VALUE = "Some value";
    private static final String SECTION = "Section";
    private static final Path basePath = Paths.get("src", "test", "resources", "expected_output", "portfolio");

    private HomepageContent saveHomepageContent() {
        HomepageContent homepageContent = new HomepageContent();
        homepageContent.setHomepageSection(SECTION);
        homepageContent.setHomepageValue(HOMEPAGE_VALUE);
        return homepageContentRepository.saveAndFlush(homepageContent);
    }

    @Test
    void retrieveHomepageContent_Success() throws Exception {
        HomepageContent homepageContent = saveHomepageContent();
        String actualResponse = mvc.perform(get(API_PORTFOLIO + API_VERSION_1 + RETRIEVE_URL + "/" + SECTION)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(basePath.resolve("retrieveHomepageContent_success.json"));
        expectedResponse = expectedResponse.replace("\"#homepageContentId#\"",
                homepageContent.getHomepageContentId().toString());
        log.info(EXPECTED_RESPONSE + expectedResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    void retrieveHomepageContent_Failure() throws Exception {
        String actualResponse =
                mvc.perform(get(API_PORTFOLIO + API_VERSION_1 + RETRIEVE_URL + "/test")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is4xxClientError())
                        .andReturn().getResponse().getContentAsString();
        log.info(ACTUAL_RESPONSE + writeValueAsString(actualResponse));

        String expectedResponse = Files.readString(basePath.resolve("retrieveHomepageContent_notFound_error.json"));
        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

}
