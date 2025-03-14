package com.example.portfolio.service.repository;

import com.example.portfolio.service.model.HomepageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomepageContentRepository extends JpaRepository<HomepageContent, Integer> {

    Optional<HomepageContent> findByHomepageSection(String homepageSection);
}
