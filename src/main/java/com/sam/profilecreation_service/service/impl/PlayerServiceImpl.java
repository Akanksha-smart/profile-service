package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.dto.PlayerDTO;
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
    public PlayerEntity convertToPlayerEntity(PlayerDTO playerDTO) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(playerDTO.getId());
        playerEntity.setName(playerDTO.getName());
        playerEntity.setDateOfBirth(playerDTO.getDateOfBirth());
        playerEntity.setSpecialization(playerDTO.getSpecialization());
        playerEntity.setGender(playerDTO.getGender());
        playerEntity.setCountry(playerDTO.getCountry());
      playerEntity.setTeamid(playerDTO.getTeamId());
      playerEntity.setProfilePicture(playerDTO.getProfilePicture());
        return playerEntity;
    }

    @Override
    public PlayerDTO convertToPlayerDTO(PlayerEntity playerEntity) {
        return new PlayerDTO(
                playerEntity.getId(),
                playerEntity.getName(),
                playerEntity.getDateOfBirth(),
                playerEntity.getSpecialization(),
                playerEntity.getGender(),
                playerEntity.getCountry(),
                playerEntity.getTeamid(),
                playerEntity.getProfilePicture(),
                playerEntity.isPlaying(),
                playerEntity.isOverseas(),
                playerEntity.isBackup()
        );
    }

    @Override
    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        PlayerEntity playerEntity = convertToPlayerEntity(playerDTO);
        PlayerEntity savedPlayerEntity = playerRepository.save(playerEntity);
        return convertToPlayerDTO(savedPlayerEntity);
    }

    @Override
    public PlayerDTO getPlayerById(Long id) {
        Optional<PlayerEntity> playerOpt = playerRepository.findById(id);
        if (playerOpt.isPresent()) {
            return convertToPlayerDTO(playerOpt.get());
        } else {
            throw new EntityNotFoundException("Player not found with id: " + id);
        }
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        List<PlayerEntity> playerEntities = playerRepository.findAll();


        return playerEntities.stream()
                .map(this::convertToPlayerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerDTO updatePlayer(PlayerDTO playerDTO, Long id) {

        PlayerEntity playerEntity = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));

        playerEntity.setName(playerDTO.getName());
        playerEntity.setGender(playerDTO.getGender());
        playerEntity.setCountry(playerDTO.getCountry());
        playerEntity.setDateOfBirth(playerDTO.getDateOfBirth());
        playerEntity.setSpecialization(playerDTO.getSpecialization());
        playerEntity.setProfilePicture(playerDTO.getProfilePicture());
         playerEntity.setTeamid(playerDTO.getTeamId());
        playerRepository.save(playerEntity);

        return convertToPlayerDTO(playerEntity);
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
    public List<PlayerDTO> getPlayersByCountry(String country) {
            List<PlayerEntity> playerEntities = playerRepository.findByCountry(country);
            return playerEntities.stream()
                    .map(this::convertToPlayerDTO)
                    .collect(Collectors.toList());
        }

}
