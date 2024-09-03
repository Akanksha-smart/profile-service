package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.dto.CoachDTO;
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
    public CoachDTO convertToCoachDTO(CoachEntity coachEntity) {
        CoachDTO coachDTO = new CoachDTO();
        coachDTO.setCoachId(coachEntity.getCoachId());
        coachDTO.setCoachName(coachEntity.getCoachName());
        coachDTO.setCountry(coachEntity.getCountry());
        //coachDTO.setTeamId(coachEntity.getTeamId());

        coachDTO.setTeamId(coachEntity.getTeamId());
        return coachDTO;
    }

    @Override
    public CoachEntity convertToCoachEntity(CoachDTO coachDTO) {
        CoachEntity coachEntity = new CoachEntity();
        coachEntity.setCoachId(coachDTO.getCoachId());
        coachEntity.setCoachName(coachDTO.getCoachName());
        coachEntity.setCountry(coachDTO.getCountry());
        coachEntity.setTeamId(coachDTO.getTeamId());
        return coachEntity;
    }

    @Override
    public CoachDTO createCoach(CoachDTO coachDTO) {
        CoachEntity coachEntity = convertToCoachEntity(coachDTO);
        CoachEntity savedCoachEntity = coachRepository.save(coachEntity);
        return convertToCoachDTO(savedCoachEntity);
    }

    @Override
    public CoachDTO findCoachById(Integer id) {
        CoachEntity coachEntity = coachRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Coach not found with id: " + id));
        return convertToCoachDTO(coachEntity);
    }

    @Override
    public List<CoachDTO> findAllCoaches() {
        List<CoachEntity> coachEntities = coachRepository.findAll();
        return coachEntities.stream()
                .map(this::convertToCoachDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CoachDTO updateCoach(CoachDTO coachDTO, Long id) {
        Optional<CoachEntity> coachEntity = coachRepository.findById(Math.toIntExact(id));
        if (coachEntity.isPresent()) {
            CoachEntity coachEntity1 = coachEntity.get();
            coachEntity1.setCoachName(coachDTO.getCoachName());
            coachEntity1.setCountry(coachDTO.getCountry());
            coachEntity1.setTeamId(coachDTO.getTeamId());
            coachRepository.save(coachEntity1);
            return convertToCoachDTO(coachEntity1);
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
