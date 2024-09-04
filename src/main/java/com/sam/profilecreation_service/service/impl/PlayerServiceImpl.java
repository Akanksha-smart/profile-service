package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.service.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;


    @Override
    public PlayerEntity createPlayer(PlayerEntity playerDTO) {
        return playerRepository.save(playerDTO);

    }

    @Override
    public PlayerEntity getPlayerById(Long id) {
        Optional<PlayerEntity> playerOpt = playerRepository.findById(id);
        if (playerOpt.isPresent()) {
            return playerOpt.get();
        } else {
            throw new EntityNotFoundException("Player not found with id: " + id);
        }
    }

    @Override
    public List<PlayerEntity> getAllPlayers() {
       return playerRepository.findAll();
    }

    @Override
    public PlayerEntity updatePlayer(PlayerEntity playerDTO, Long id) {

        PlayerEntity playerEntity = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));

        playerEntity.setName(playerDTO.getName());
        playerEntity.setGender(playerDTO.getGender());
        playerEntity.setCountry(playerDTO.getCountry());
        playerEntity.setDateOfBirth(playerDTO.getDateOfBirth());
        playerEntity.setSpecialization(playerDTO.getSpecialization());
        playerEntity.setProfilePicture(playerDTO.getProfilePicture());
         //playerEntity.setTeamid(playerDTO.getTeamId());
        playerRepository.save(playerEntity);

        return playerEntity;
    }

    @Override
    public void deletePlayer(Long id) {
        Optional<PlayerEntity> playerOpt = playerRepository.findById(id);
        if (playerOpt.isPresent()) {
            playerRepository.delete(playerOpt.get());
        } else {
            throw new EntityNotFoundException("Player not found with id: " + id);
        }
    }

    @Override
    public List<PlayerEntity> getPlayersByCountry(String country) {
        return  playerRepository.findByCountry(country);

        }

}
