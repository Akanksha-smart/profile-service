package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.dto.PlayerDTO;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.service.PlayerService;
import com.sam.profilecreation_service.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;



//    @Override
//    public PlayerEntity convertToPlayerEntity(PlayerDTO playerDTO) {
//       PlayerEntity playerEntity = new PlayerEntity();
//       playerEntity.setId(playerDTO.getId());
//       playerEntity.setName(playerDTO.getName());
//       playerEntity.setGender(playerDTO.getGender());
//       playerEntity.setCountry(playerDTO.getCountry());
//       playerEntity.setDateOfBirth(playerDTO.getDateOfBirth());
//       playerEntity.setSpecialization(playerDTO.getSpecialization());
////       playerEntity.setProfilePicture(playerDTO.getProfilePicture());
//       playerEntity.setTeam(playerDTO.getTeam());
//       return playerEntity;
//    }

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
                playerEntity.isPlaying()
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

        // Check if player exists
        if (playerOpt.isPresent()) {
            // Convert to DTO and return
            return convertToPlayerDTO(playerOpt.get());
        } else {
            // Handle case when player is not found
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
//        Optional<PlayerEntity> playerEntityOptional = Optional.ofNullable(playerRepository.findById(player.getId()));
//        if (playerEntityOptional.isPresent()) {
//            PlayerEntity playerEntity = playerEntityOptional.get();
//            playerEntity.setName(player.getName());
//            playerEntity.setGender(player.getGender());
//            playerEntity.setCountry(player.getCountry());
//            playerEntity.setDateOfBirth(player.getDateOfBirth());
//            playerEntity.setSpecialization(player.getSpecialization());
//           playerEntity.setProfilePicture(player.getProfilePicture());
////           playerEntity.setTeam(player.getTeam());
//            playerRepository.save(playerEntity);
//            return convertToPlayerDTO(playerEntity);
//        }
//        else
//            throw new EntityNotFoundException("Player not found with id: " + id);

        PlayerEntity playerEntity = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));

        // Update the player entity with new values
        playerEntity.setName(playerDTO.getName());
        playerEntity.setGender(playerDTO.getGender());
        playerEntity.setCountry(playerDTO.getCountry());
        playerEntity.setDateOfBirth(playerDTO.getDateOfBirth());
        playerEntity.setSpecialization(playerDTO.getSpecialization());
        playerEntity.setProfilePicture(playerDTO.getProfilePicture());
        // If needed, set the team, but make sure it's managed correctly if updating
        // playerEntity.setTeam(playerDTO.getTeam());

        // Save the updated entity
        playerRepository.save(playerEntity);

        // Convert the updated entity to DTO and return
        return convertToPlayerDTO(playerEntity);
    }

    @Override
    public void deletePlayer(Long id) {
        Optional<PlayerEntity> playerOpt = playerRepository.findById(id);

        // Check if player exists
        if (playerOpt.isPresent()) {
            // Delete the player
            playerRepository.delete(playerOpt.get());
        } else {
            // Throw an exception if player is not found
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
