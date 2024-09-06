package com.sam.profilecreation_service.service;


import com.sam.profilecreation_service.entity.PlayerEntity;

import java.util.List;

public interface PlayerService {

    PlayerEntity createPlayer(PlayerEntity playerDTO);

    PlayerEntity getPlayerById(Long id);
    List<PlayerEntity> getAllPlayers();

    PlayerEntity updatePlayer(PlayerEntity player, Long id);

    void deletePlayer(Long id);



    List<PlayerEntity> getPlayersByCountry(String country);

    List<PlayerEntity> getPlayersWithNoTeam();
}
