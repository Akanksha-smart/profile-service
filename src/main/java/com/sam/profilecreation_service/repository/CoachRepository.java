package com.sam.profilecreation_service.repository;

import com.sam.profilecreation_service.entity.CoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {
    //CoachEntity findById(Long coachId);
}
