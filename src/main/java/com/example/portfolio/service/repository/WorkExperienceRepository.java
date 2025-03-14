package com.example.portfolio.service.repository;

import com.example.portfolio.service.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Integer> {

    @Query("SELECT workExp FROM WorkExperience workExp ORDER BY workExp.startDate DESC")
    List<WorkExperience> findAllOrderByStartDateDesc();
}
