package com.example.portfolio.service.model;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.portfolio.service.constant.JpaConstant.LONG_TEXT;
import static com.example.portfolio.service.constant.model.HomepageContentModelConstant.*;

@Data
@Entity
@Table(name = HOMEPAGE_CONTENT_TABLE)
public class HomepageContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = HOMEPAGE_CONTENT_ID, nullable = false)
    private Integer homepageContentId;
    @Column(name = HOMEPAGE_SECTION, nullable = false)
    private String homepageSection;
    @Lob
    @Column(name = HOMEPAGE_VALUE, columnDefinition = LONG_TEXT, nullable = false)
    private String homepageValue;

}
