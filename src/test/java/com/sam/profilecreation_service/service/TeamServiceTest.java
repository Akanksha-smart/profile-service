package com.sam.profilecreation_service.service;

//import com.sam.profilecreation_service.dto.CoachDTO;
//import com.sam.profilecreation_service.dto.PlayerDTO;
//import com.sam.profilecreation_service.dto.TeamDTO;
import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.repository.TeamRepository;
import com.sam.profilecreation_service.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Mock
    private PlayerService playerService;

    @Mock
    private CoachService coachService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void createTeamTest() {
//        TeamDTO teamDTO = new TeamDTO();
//        TeamEntity teamEntity = new TeamEntity();
//
//        when(teamRepository.save(any(TeamEntity.class))).thenReturn(teamEntity);
//        when(playerService.convertToPlayerEntity(any(PlayerDTO.class))).thenReturn(new PlayerEntity());
//        when(coachService.convertToCoachEntity(any(CoachDTO.class))).thenReturn(new CoachEntity());
//
//        TeamDTO result = teamService.createTeam(teamDTO);
//
//        assertNotNull(result);
//        verify(teamRepository, times(1)).save(any(TeamEntity.class));
//    }

//    @Test
//    public void getTeamByIdTest() {
//        // Arrange
//        Long teamId = 1L;
//        TeamEntity mockTeam = new TeamEntity();
//        mockTeam.setId(teamId);
//        mockTeam.setName("Test Team");
//
//        when(teamRepository.findById(teamId)).thenReturn(Optional.of(mockTeam));
//
//        // Act
//        TeamDTO result = teamService.getTeamById(teamId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(teamId, result.getId());
//        assertEquals("Test Team", result.getName());
//    }
//
//    @Test
//    public void saveTeamTest() {
//
//        TeamEntity team = new TeamEntity();
//        team.setName("New Team");
//
//        // Mocking the repository's save method to return the team entity
//        when(teamRepository.save(any(TeamEntity.class))).thenReturn(team);
//
////        // Act
////        TeamEntity result = teamService.createTeam(team)
////
////        // Assert
////        assertNotNull(result);
////        assertEquals("New Team", result.getName());
    //}


}
