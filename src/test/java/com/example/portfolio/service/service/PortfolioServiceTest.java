package com.example.portfolio.service.service;

import com.example.portfolio.service.dto.RetrieveHomepageContentResponse;
import com.example.portfolio.service.exception.PortfolioException;
import com.example.portfolio.service.model.HomepageContent;
import com.example.portfolio.service.repository.HomepageContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.example.portfolio.service.exception.PortfolioErrorMessage.HOMEPAGE_SECTION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PortfolioServiceTest {
    private static final String HOMEPAGE_VALUE = "Some value";
    private static final String SECTION = "Section";

    @Mock
    private HomepageContentRepository homepageContentRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(portfolioService, "mapper", new ModelMapper());
    }

    private static HomepageContent getHomepageContent() {
        HomepageContent homepageContent = new HomepageContent();
        homepageContent.setHomepageContentId(1);
        homepageContent.setHomepageSection(SECTION);
        homepageContent.setHomepageValue(HOMEPAGE_VALUE);
        return homepageContent;
    }

    @Test
    void retrieveHomepageContent_Success() {
        HomepageContent homepageContent = getHomepageContent();
        when(homepageContentRepository.findByHomepageSection(SECTION)).thenReturn(Optional.of(homepageContent));

        RetrieveHomepageContentResponse actualResponse = portfolioService.retrieveContent(SECTION);

        verify(homepageContentRepository, times(1)).findByHomepageSection(SECTION);
        assertNotNull(actualResponse);
        assertEquals(homepageContent.getHomepageContentId(), actualResponse.getHomepageContentId());
        assertEquals(homepageContent.getHomepageSection(), actualResponse.getHomepageSection());
        assertEquals(homepageContent.getHomepageValue(), actualResponse.getHomepageValue());
    }

    @Test
    void retrieveHomepageContent_Failure() {
        when(homepageContentRepository.findByHomepageSection(SECTION)).thenReturn(Optional.empty());

        PortfolioException ex = assertThrows(PortfolioException.class, () -> portfolioService.retrieveContent(SECTION));

        assertEquals(HOMEPAGE_SECTION_NOT_FOUND, ex.getErrorMessage());
        assertEquals(HOMEPAGE_SECTION_NOT_FOUND.getHttpStatus(), ex.getErrorMessage().getHttpStatus());
        assertEquals(HOMEPAGE_SECTION_NOT_FOUND.getErrorCode(), ex.getErrorMessage().getErrorCode());
        assertEquals(HOMEPAGE_SECTION_NOT_FOUND.getErrorMessage(), ex.getErrorMessage().getErrorMessage());
    }
}
