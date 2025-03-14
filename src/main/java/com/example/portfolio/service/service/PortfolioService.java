package com.example.portfolio.service.service;

import com.example.portfolio.service.dto.RetrieveHomepageContentResponse;
import com.example.portfolio.service.exception.PortfolioException;
import com.example.portfolio.service.model.HomepageContent;
import com.example.portfolio.service.repository.HomepageContentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.portfolio.service.exception.PortfolioErrorMessage.HOMEPAGE_SECTION_NOT_FOUND;

@Service
public class PortfolioService {

    @Autowired
    private HomepageContentRepository homepageContentRepository;
    @Autowired
    private ModelMapper mapper;

    public RetrieveHomepageContentResponse retrieveContent(String section) {
        HomepageContent homepageContent = homepageContentRepository.findByHomepageSection(section)
                .orElseThrow(() -> new PortfolioException(HOMEPAGE_SECTION_NOT_FOUND));

        return mapper.map(homepageContent, RetrieveHomepageContentResponse.class);
    }

}
