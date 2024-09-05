package com.sam.profilecreation_service.service;

import com.sam.profilecreation_service.entity.CoachEntity;

import java.util.List;

public interface CoachService {

    CoachEntity createCoach(CoachEntity CoachEntity);

    //CoachEntity findCoachById(Integer id);

    CoachEntity findCoachById(Long id);

    List<CoachEntity> findAllCoaches();
    CoachEntity updateCoach(CoachEntity CoachEntity, Long id);
    void deleteCoach(Long id);

}
