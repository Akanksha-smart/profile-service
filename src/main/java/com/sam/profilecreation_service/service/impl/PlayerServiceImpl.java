package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.service.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    // Constructor Injection for better practices
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerEntity createPlayer(PlayerEntity playerDTO) {
        // Additional validation if needed
        return playerRepository.save(playerDTO);
    }

    @Override
    public PlayerEntity getPlayerById(Long id) {
        // Directly use orElseThrow to avoid unnecessary if-else blocks
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
    }

    @Override
    public List<PlayerEntity> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public PlayerEntity updatePlayer(PlayerEntity playerDTO, Long id) {
        // Find player and update all required fields in one go
        PlayerEntity existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));

        // You can create a method to map playerDTO fields into existingPlayer for better code reuse
        existingPlayer.setName(playerDTO.getName());
        existingPlayer.setGender(playerDTO.getGender());
        existingPlayer.setCountry(playerDTO.getCountry());
        existingPlayer.setDateOfBirth(playerDTO.getDateOfBirth());
        existingPlayer.setSpecialization(playerDTO.getSpecialization());
        existingPlayer.setProfilePicture(playerDTO.getProfilePicture());
        existingPlayer.setTeamid(playerDTO.getTeamid());

        // Save the updated entity
        return playerRepository.save(existingPlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        // Directly throw an exception if the player is not found
        PlayerEntity player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
        playerRepository.delete(player);
    }

    @Override
    public List<PlayerEntity> getPlayersByCountry(String country) {
        return playerRepository.findByCountry(country);
    }

    @Override
    public List<PlayerEntity> getPlayersWithNoTeam() {
        return playerRepository.findByTeamidIsNull();
    }
}
