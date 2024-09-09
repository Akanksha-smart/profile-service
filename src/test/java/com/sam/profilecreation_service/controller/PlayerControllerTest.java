package com.sam.profilecreation_service.controller;


import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.service.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlayerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    public void testCreatePlayer() throws Exception {
        PlayerEntity player = new PlayerEntity();
        player.setId(1L);

        when(playerService.createPlayer(any(PlayerEntity.class))).thenReturn(player);

        mockMvc.perform(post("/api/players/create")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetPlayerById() throws Exception {
        PlayerEntity player = new PlayerEntity();
        player.setId(1L);

        when(playerService.getPlayerById(anyLong())).thenReturn(player);

        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetPlayerByIdNotFound() throws Exception {
        when(playerService.getPlayerById(anyLong())).thenThrow(new EntityNotFoundException("Player not found"));

        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllPlayers() throws Exception {
        PlayerEntity player1 = new PlayerEntity();
        PlayerEntity player2 = new PlayerEntity();
        List<PlayerEntity> players = Arrays.asList(player1, player2);

        when(playerService.getAllPlayers()).thenReturn(players);

        mockMvc.perform(get("/api/players/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testUpdatePlayer() throws Exception {
        PlayerEntity player = new PlayerEntity();
        player.setId(1L);

        when(playerService.updatePlayer(any(PlayerEntity.class), anyLong())).thenReturn(player);

        mockMvc.perform(put("/api/players/update/1")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdatePlayerNotFound() throws Exception {
        when(playerService.updatePlayer(any(PlayerEntity.class), anyLong())).thenThrow(new EntityNotFoundException("Player not found"));

        mockMvc.perform(put("/api/players/update/1")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePlayer() throws Exception {
        doNothing().when(playerService).deletePlayer(anyLong());

        mockMvc.perform(delete("/api/players/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePlayerNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Player not found")).when(playerService).deletePlayer(anyLong());

        mockMvc.perform(delete("/api/players/delete/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPlayersByCountry() throws Exception {
        PlayerEntity player1 = new PlayerEntity();
        PlayerEntity player2 = new PlayerEntity();
        List<PlayerEntity> players = Arrays.asList(player1, player2);

        when(playerService.getPlayersByCountry(anyString())).thenReturn(players);

        mockMvc.perform(get("/api/players/country/USA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetPlayersWithNoTeam() throws Exception {
        PlayerEntity player1 = new PlayerEntity();
        PlayerEntity player2 = new PlayerEntity();
        List<PlayerEntity> players = Arrays.asList(player1, player2);

        when(playerService.getPlayersWithNoTeam()).thenReturn(players);

        mockMvc.perform(get("/api/players/no-team"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

}
