package com.sam.profilecreation_service.controller;

import com.sam.profilecreation_service.dto.TeamRegisterDTO;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TeamControllerTest {


    private MockMvc mockMvc;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    public void testCreateTeam() throws Exception {
        TeamRegisterDTO teamRegisterDTO = new TeamRegisterDTO();
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(1L);

        when(teamService.createTeam(any(TeamRegisterDTO.class))).thenReturn(teamEntity);

        mockMvc.perform(post("/api/teams/create")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetTeamById() throws Exception {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(1L);

        when(teamService.getTeamById(anyLong())).thenReturn(teamEntity);

        mockMvc.perform(get("/api/teams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetTeamByIdNotFound() throws Exception {
        when(teamService.getTeamById(anyLong())).thenThrow(new EntityNotFoundException("Team not found"));

        mockMvc.perform(get("/api/teams/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Team not found with ID: 1"));
    }

    @Test
    public void testGetAllTeams() throws Exception {
        TeamEntity team1 = new TeamEntity();
        TeamEntity team2 = new TeamEntity();
        List<TeamEntity> teams = Arrays.asList(team1, team2);

        when(teamService.getAllTeams()).thenReturn(teams);

        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetTeamByCoachName() throws Exception {
        Integer coachId = 1;

        when(teamService.getTeamsByCoachName(anyString())).thenReturn(coachId);

        mockMvc.perform(get("/api/teams/coach/JohnDoe"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void testGetTeamByCoachNameNotFound() throws Exception {
        when(teamService.getTeamsByCoachName(anyString())).thenReturn(null);

        mockMvc.perform(get("/api/teams/coach/Unknown"))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void testUpdateTeam() throws Exception {
//        // Create a sample team entity to be updated
//        TeamEntity teamEntity = new TeamEntity();
//        teamEntity.setId(1L);
//        teamEntity.setName("Updated Team Name"); // Add other necessary fields as per your entity
//
//        // Mocking the service layer's updateTeam method
//        doNothing().when(teamService).updateTeam(any(TeamEntity.class), anyLong());
//
//        // Convert teamEntity to JSON
//        String teamEntityJson = "{"
//                + "\"id\": 1,"
//                + "\"name\": \"Updated Team Name\""
//                + "}";
//
//        // Perform the PUT request
//        mockMvc.perform(put("/api/teams/update/1")
//                        .contentType("application/json")
//                        .content(teamEntityJson))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Team updated successfully"));
//    }

    @Test
    public void testUpdateTeamNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Team not found")).when(teamService).updateTeam(any(TeamEntity.class), anyLong());

        mockMvc.perform(put("/api/teams/update/1")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Team not found with ID: 1"));
    }

    @Test
    public void testDeleteTeam() throws Exception {
        doNothing().when(teamService).deleteTeam(anyLong());

        mockMvc.perform(delete("/api/teams/delete/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Team deleted successfully"));
    }

    @Test
    public void testDeleteTeamNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Team not found")).when(teamService).deleteTeam(anyLong());

        mockMvc.perform(delete("/api/teams/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Team not found with ID: 1"));
    }

    @Test
    public void testAddPlayerToTeam() throws Exception {
        doNothing().when(teamService).addPlayerToTeam(anyLong(), anyLong());

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);

        mockMvc.perform(post("/api/teams/1/players")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Player added to team successfully"));
    }

    @Test
    public void testAddPlayerToTeamNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Team not found")).when(teamService).addPlayerToTeam(anyLong(), anyLong());

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);

        mockMvc.perform(post("/api/teams/1/players")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Team not found with ID: 1"));
    }

// Uncomment this test if you implement the addCoachToTeam endpoint
    /*
    @Test
    public void testAddCoachToTeam() throws Exception {
        doNothing().when(teamService).addCoachToTeam(anyLong(), anyLong());

        CoachEntity coachEntity = new CoachEntity();
        coachEntity.setCoachId(1L);

        mockMvc.perform(post("/api/teams/1/coach")
                .contentType("application/json")
                .content("{\"coachId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Coach added to team successfully"));
    }

    @Test
    public void testAddCoachToTeamNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Team not found")).when(teamService).addCoachToTeam(anyLong(), anyLong());

        CoachEntity coachEntity = new CoachEntity();
        coachEntity.setCoachId(1L);

        mockMvc.perform(post("/api/teams/1/coach")
                .contentType("application/json")
                .content("{\"coachId\":1}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Team not found with ID: 1"));
    }
     */




}
