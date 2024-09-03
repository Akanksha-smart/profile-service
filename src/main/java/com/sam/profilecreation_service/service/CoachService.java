package com.sam.profilecreation_service.service;

import com.sam.profilecreation_service.dto.CoachDTO;
import com.sam.profilecreation_service.entity.CoachEntity;

import java.util.List;

public interface CoachService {
    CoachDTO convertToCoachDTO(CoachEntity coachEntity);

    CoachEntity convertToCoachEntity(CoachDTO coachDTO);

    CoachDTO createCoach(CoachDTO coachDTO);

    CoachDTO findCoachById(Integer id);
    List<CoachDTO> findAllCoaches();
    CoachDTO updateCoach(CoachDTO coachDTO, Long id);
    void deleteCoach(Long id);

}
