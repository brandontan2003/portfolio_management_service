package com.example.portfolio.service.controller;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.dto.RetrieveHomepageContentResponse;
import com.example.portfolio.service.exception.PortfolioException;
import com.example.portfolio.service.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_SUCCESS;
import static com.example.portfolio.service.exception.PortfolioErrorMessage.HOMEPAGE_SECTION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PortfolioControllerTest {

    @InjectMocks
    private PortfolioController portfolioController;

    @Mock
    private PortfolioService portfolioService;

    private static final String HOMEPAGE_VALUE = "Some value";
    private static final String SECTION = "Section";

    @Test
    void retrieveContent_ReturnHomepageValue_Success() {
        RetrieveHomepageContentResponse mockHomepageContent = getMockHomepageContent();
        when(portfolioService.retrieveContent(SECTION)).thenReturn(mockHomepageContent);

        ResponsePayload<RetrieveHomepageContentResponse> actualResponse =
                portfolioController.retrieveContent(SECTION);

        assertNotNull(actualResponse);
        assertEquals(STATUS_SUCCESS, actualResponse.getStatus());
        RetrieveHomepageContentResponse result = actualResponse.getResult();
        assertEquals(mockHomepageContent.getHomepageContentId(), result.getHomepageContentId());
        assertEquals(mockHomepageContent.getHomepageSection(), result.getHomepageSection());
        assertEquals(mockHomepageContent.getHomepageValue(), result.getHomepageValue());
    }

    private static RetrieveHomepageContentResponse getMockHomepageContent() {
        return new RetrieveHomepageContentResponse(1, SECTION, HOMEPAGE_VALUE);
    }

    @Test
    void retrieveContent_HomepageSectionNotFound_Error() {
        when(portfolioService.retrieveContent(SECTION)).thenThrow(new PortfolioException(HOMEPAGE_SECTION_NOT_FOUND));


        PortfolioException ex = assertThrows(PortfolioException.class,
                () -> portfolioController.retrieveContent(SECTION));

        assertEquals(HOMEPAGE_SECTION_NOT_FOUND, ex.getErrorMessage());
        assertEquals(HOMEPAGE_SECTION_NOT_FOUND.getHttpStatus(), ex.getErrorMessage().getHttpStatus() );
        assertEquals(HOMEPAGE_SECTION_NOT_FOUND.getErrorCode(), ex.getErrorMessage().getErrorCode() );
        assertEquals(HOMEPAGE_SECTION_NOT_FOUND.getErrorMessage(), ex.getErrorMessage().getErrorMessage() );
    }
}
