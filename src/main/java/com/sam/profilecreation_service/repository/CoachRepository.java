package com.sam.profilecreation_service.repository;

import com.sam.profilecreation_service.entity.CoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {


    @Query("SELECT c.coachId FROM CoachEntity c WHERE c.name = :coachName")
    Long findCoachIdByCoachName(@Param("coachName") String coachName);


}
