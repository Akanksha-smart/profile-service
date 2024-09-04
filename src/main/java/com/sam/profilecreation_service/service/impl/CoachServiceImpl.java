package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.repository.CoachRepository;
import com.sam.profilecreation_service.service.CoachService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private  CoachRepository coachRepository;


    @Override
    public CoachEntity createCoach(CoachEntity coachEntity) {
       CoachEntity coachEntity1 =  coachRepository.save(coachEntity);
        return coachEntity1;
    }

    @Override
    public CoachEntity findCoachById(Integer id) {
        CoachEntity coachEntity = coachRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Coach not found with id: " + id));
        return coachEntity;
    }

    @Override
    public List<CoachEntity> findAllCoaches() {
        return coachRepository.findAll();
    }


    @Override
    public CoachEntity updateCoach(CoachEntity CoachEntity, Long id) {
        Optional<CoachEntity> coachEntity = coachRepository.findById(Math.toIntExact(id));
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
      Optional<CoachEntity> coachEntity = coachRepository.findById(Math.toIntExact(id));
      if (coachEntity.isPresent()) {
          coachRepository.delete(coachEntity.get());
      }else
          throw new EntityNotFoundException("Coach not found with id: " + id);
    }
}
