package com.sam.profilecreation_service.service;


import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.service.impl.PlayerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlayer_Success() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setName("John Doe");

        when(playerRepository.save(any(PlayerEntity.class))).thenReturn(playerEntity);

        PlayerEntity createdPlayer = playerService.createPlayer(playerEntity);

        assertNotNull(createdPlayer);
        assertEquals("John Doe", createdPlayer.getName());
        verify(playerRepository, times(1)).save(any(PlayerEntity.class));
    }

    @Test
    void testGetPlayerById_Success() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerEntity.setName("John Doe");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(playerEntity));

        PlayerEntity foundPlayer = playerService.getPlayerById(1L);

        assertNotNull(foundPlayer);
        assertEquals(1L, foundPlayer.getId());
        assertEquals("John Doe", foundPlayer.getName());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPlayerById_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> playerService.getPlayerById(1L));

        assertEquals("Player not found with id: 1", exception.getMessage());
        verify(playerRepository, times(1)).findById(1L);
    }

//    @Test
//    void testGetAllPlayers_Success() {
//        List<PlayerEntity> players = new ArrayList<>();
//        players.add(new PlayerEntity(1L, "John", "Male", "USA", null, null, null, null, null,"PLAYER"));
//        players.add(new PlayerEntity(2L, "Jane", "Female", "UK", null, null, null, null, null,"PLAYER"));
//
//        when(playerRepository.findAll()).thenReturn(players);
//
//        List<PlayerEntity> allPlayers = playerService.getAllPlayers();
//
//        assertNotNull(allPlayers);
//        assertEquals(2, allPlayers.size());
//        verify(playerRepository, times(1)).findAll();
//    }

    @Test
    void testUpdatePlayer_Success() {
        PlayerEntity existingPlayer = new PlayerEntity();
        existingPlayer.setId(1L);
        existingPlayer.setName("Old Name");

        PlayerEntity playerDTO = new PlayerEntity();
        playerDTO.setName("New Name");
        playerDTO.setCountry("USA");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(existingPlayer));
        when(playerRepository.save(any(PlayerEntity.class))).thenReturn(existingPlayer);

        PlayerEntity updatedPlayer = playerService.updatePlayer(playerDTO, 1L);

        assertNotNull(updatedPlayer);
        assertEquals("New Name", updatedPlayer.getName());
        assertEquals("USA", updatedPlayer.getCountry());
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).save(existingPlayer);
    }

    @Test
    void testUpdatePlayer_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        PlayerEntity playerDTO = new PlayerEntity();
        playerDTO.setName("New Name");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> playerService.updatePlayer(playerDTO, 1L));

        assertEquals("Player not found with id: 1", exception.getMessage());
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, never()).save(any(PlayerEntity.class));
    }

    @Test
    void testDeletePlayer_Success() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(playerEntity));

        playerService.deletePlayer(1L);

        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).delete(playerEntity);
    }

    @Test
    void testDeletePlayer_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> playerService.deletePlayer(1L));

        assertEquals("Player not found with id: 1", exception.getMessage());
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, never()).delete(any(PlayerEntity.class));
    }

//    @Test
//    void testGetPlayersByCountry_Success() {
//        List<PlayerEntity> players = new ArrayList<>();
//        players.add(new PlayerEntity(1L, "John", "Male", "USA", null, null, null, null, null));
//
//        when(playerRepository.findByCountry("USA")).thenReturn(players);
//
//        List<PlayerEntity> result = playerService.getPlayersByCountry("USA");
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(playerRepository, times(1)).findByCountry("USA");
//    }

//    @Test
//    void testGetPlayersWithNoTeam_Success() {
//        List<PlayerEntity> players = new ArrayList<>();
//        players.add(new PlayerEntity(1L, "John", "Male", "USA", null, null, null, null, null));
//
//        when(playerRepository.findByTeamidIsNull()).thenReturn(players);
//
//        List<PlayerEntity> result = playerService.getPlayersWithNoTeam();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(playerRepository, times(1)).findByTeamidIsNull();
//    }

}
