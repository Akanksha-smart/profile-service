package com.sam.profilecreation_service.service;

import com.sam.profilecreation_service.dto.PlayerDTO;
import com.sam.profilecreation_service.entity.PlayerEntity;

import java.util.List;

public interface PlayerService {
    PlayerEntity convertToPlayerEntity(PlayerDTO playerDTO);

    PlayerDTO convertToPlayerDTO(PlayerEntity playerEntity);

    PlayerDTO createPlayer(PlayerDTO playerDTO);

    PlayerDTO getPlayerById(Long id);
    List<PlayerDTO> getAllPlayers();

    PlayerDTO updatePlayer(PlayerDTO player, Long id);

    void deletePlayer(Long id);



    List<PlayerDTO> getPlayersByCountry(String country);

}
