package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.repository.CoachRepository;
import com.sam.profilecreation_service.service.CoachService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private  CoachRepository coachRepository;


    @Override
    public CoachEntity createCoach(CoachEntity coachEntity) {
        return coachRepository.save(coachEntity);
    }

    @Override
    public CoachEntity findCoachById(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coach not found with id: " + id));
    }

    @Override
    public List<CoachEntity> findAllCoaches() {
        return coachRepository.findAll();
    }


    @Override
    public CoachEntity updateCoach(CoachEntity CoachEntity, Long id) {
        Optional<CoachEntity> coachEntity = coachRepository.findById(id);
        if (coachEntity.isPresent()) {
            CoachEntity coachEntity1 = coachEntity.get();
//            coachEntity1.setName(CoachEntity.getCoachName());
            coachEntity1.setCountry(CoachEntity.getCountry());
            coachEntity1.setTeamId(CoachEntity.getTeamId());
            coachRepository.save(coachEntity1);
            return coachEntity1;
        }
        else
            throw new EntityNotFoundException("Coach not found with id: " + id);

    }

    @Override
    public void deleteCoach(Long id) {
      Optional<CoachEntity> coachEntity = coachRepository.findById(id);
      if (coachEntity.isPresent()) {
          coachRepository.delete(coachEntity.get());
      }else
          throw new EntityNotFoundException("Coach not found with id: " + id);
    }
}
