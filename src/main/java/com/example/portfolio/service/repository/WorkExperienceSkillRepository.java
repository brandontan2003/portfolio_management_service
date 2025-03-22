package com.example.portfolio.service.repository;

import com.example.portfolio.service.model.WorkExperienceSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceSkillRepository extends JpaRepository<WorkExperienceSkill, Integer> {

}
