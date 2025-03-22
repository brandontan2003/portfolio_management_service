package com.example.portfolio.service.repository;

import com.example.portfolio.service.model.WorkExperienceDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceDescriptionRepository extends JpaRepository<WorkExperienceDescription, Integer> {

}
