package com.sam.profilecreation_service.repository;

import com.sam.profilecreation_service.entity.TeamEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    Integer findByCoachName(String name);
}
