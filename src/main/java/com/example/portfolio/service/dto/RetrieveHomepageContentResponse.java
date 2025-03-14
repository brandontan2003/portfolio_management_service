package com.example.portfolio.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveHomepageContentResponse {

    private Integer homepageContentId;
    private String homepageSection;
    private String homepageValue;

}
