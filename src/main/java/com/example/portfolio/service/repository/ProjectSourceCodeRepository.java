package com.example.portfolio.service.repository;

import com.example.portfolio.service.model.ProjectSourceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSourceCodeRepository extends JpaRepository<ProjectSourceCode, Integer> {

}
