package com.example.portfolio.service.repository;

import com.example.portfolio.service.model.EducationDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationDescriptionRepository extends JpaRepository<EducationDescription, Integer> {

}
