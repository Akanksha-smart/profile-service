package com.sam.profilecreation_service.service;

import com.sam.profilecreation_service.dto.PlayerDTO;
import com.sam.profilecreation_service.dto.TeamRegisterDTO;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.exception.TeamNotFoundException;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.repository.TeamRepository;
import com.sam.profilecreation_service.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeam_Success() throws Exception {

        TeamRegisterDTO teamRegisterDTO = createSampleTeamRegisterDTO();
        TeamEntity savedTeam = createSampleTeamEntity();
        when(teamRepository.save(any(TeamEntity.class))).thenReturn(savedTeam);
        when(playerRepository.save(any(PlayerEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        TeamEntity result = teamService.createTeam(teamRegisterDTO);

        assertNotNull(result);
        assertEquals(savedTeam.getId(), result.getId());
        assertEquals(teamRegisterDTO.getName(), result.getName());
        assertEquals(15, result.getPlayers().size());
        verify(teamRepository, times(2)).save(any(TeamEntity.class));
        verify(playerRepository, times(15)).save(any(PlayerEntity.class));
    }

    @Test
    void testCreateTeam_InvalidPlayerCount() {
        TeamRegisterDTO teamRegisterDTO = createSampleTeamRegisterDTO();
        teamRegisterDTO.getPlayers().remove(0);

        assertThrows(Exception.class, () -> teamService.createTeam(teamRegisterDTO));
        verify(teamRepository, never()).save(any(TeamEntity.class));
    }

    @Test
    void testCreateTeam_InvalidPlayerDistribution() {
        TeamRegisterDTO teamRegisterDTO = createSampleTeamRegisterDTO();
        teamRegisterDTO.getPlayers().forEach(p -> p.setSpecialization("Bowler"));

        assertThrows(Exception.class, () -> teamService.createTeam(teamRegisterDTO));
        verify(teamRepository, never()).save(any(TeamEntity.class));
    }

    @Test
    void testGetTeamById_Success() {
        Long teamId = 1L;
        TeamEntity team = createSampleTeamEntity();
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        TeamEntity result = teamService.getTeamById(teamId);

        assertNotNull(result);
        assertEquals(team.getId(), result.getId());
    }

    @Test
    void testGetTeamById_NotFound() {
        Long teamId = 1L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teamService.getTeamById(teamId));
    }

    @Test
    void testGetAllTeams() {

        List<TeamEntity> teams = Arrays.asList(createSampleTeamEntity(), createSampleTeamEntity());
        when(teamRepository.findAll()).thenReturn(teams);

        List<TeamEntity> result = teamService.getAllTeams();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateTeam_Success() {
        Long teamId = 1L;
        TeamEntity existingTeam = createSampleTeamEntity();
        TeamEntity updatedTeam = createSampleTeamEntity();
        updatedTeam.setName("Updated Team");

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(existingTeam));
        when(teamRepository.save(any(TeamEntity.class))).thenReturn(updatedTeam);
        when(playerRepository.findAllById(anyList())).thenReturn(existingTeam.getPlayers());

        TeamEntity result = teamService.updateTeam(updatedTeam, teamId);

        assertNotNull(result);
        assertEquals("Updated Team", result.getName());
    }

    @Test
    void testUpdateTeam_NotFound() {
        Long teamId = 1L;
        TeamEntity updatedTeam = createSampleTeamEntity();
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teamService.updateTeam(updatedTeam, teamId));
    }

    @Test
    void testDeleteTeam_Success() {
        Long teamId = 1L;
        TeamEntity team = createSampleTeamEntity();
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        teamService.deleteTeam(teamId);

        verify(teamRepository).delete(team);
    }

    @Test
    void testDeleteTeam_NotFound() {
        Long teamId = 1L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> teamService.deleteTeam(teamId));
    }

    @Test
    void testAddPlayerToTeam_Success() {
        Long teamId = 1L;
        Long playerId = 1L;
        TeamEntity team = createSampleTeamEntity();
        PlayerEntity player = new PlayerEntity();
        player.setId(playerId);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        teamService.addPlayerToTeam(teamId, playerId);


        verify(teamRepository).save(team);
        assertEquals(teamId, player.getTeamid());
    }

    @Test
    void testAddPlayerToTeam_TeamNotFound() {
        Long teamId = 1L;
        Long playerId = 1L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teamService.addPlayerToTeam(teamId, playerId));
    }

    @Test
    void testAddPlayerToTeam_PlayerNotFound() {
        Long teamId = 1L;
        Long playerId = 1L;
        TeamEntity team = createSampleTeamEntity();
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> teamService.addPlayerToTeam(teamId, playerId));
    }

    @Test
    void testGetTeamsByCoachName_Success() {
        String coachName = "John Doe";
        when(teamRepository.findByCoachName(coachName)).thenReturn(2);

        Integer result = teamService.getTeamsByCoachName(coachName);

        assertEquals(2, result);
    }

    @Test
    void testGetTeamsByCoachName_NoTeamsFound() {
        String coachName = "Jane Doe";
        when(teamRepository.findByCoachName(coachName)).thenReturn(null);

        assertThrows(TeamNotFoundException.class, () -> teamService.getTeamsByCoachName(coachName));
    }

    private TeamRegisterDTO createSampleTeamRegisterDTO() {
        TeamRegisterDTO dto = new TeamRegisterDTO();
        dto.setName("Sample Team");
        dto.setCountry("Sample Country");
        dto.setTeamCaptain("Sample Captain");
        dto.setCoachName("Sample Coach");
        dto.setOwner("Sample Owner");
        dto.setIcon("sample-icon.png");
        dto.setCoachId(1L);
        dto.setTotalPoints(100);
        dto.setPlayers(createSamplePlayerDTOs());
        return dto;
    }

    private List<PlayerDTO> createSamplePlayerDTOs() {
        List<PlayerDTO> players = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            PlayerDTO player = new PlayerDTO();
            player.setName("Player " + i);
            player.setEmail("player" + i + "@example.com");
            player.setUsername("player" + i);
            player.setDateOfBirth(LocalDate.of(1990, 1, 1));
            player.setSpecialization(i < 5 ? "Bowler" : (i < 10 ? "Batter" : "All-Rounder"));
            player.setGender("Male");
            player.setCountry(i < 10 ? "Sample Country" : "Other Country");
            player.setProfilePicture("player" + i + ".jpg");
            player.setPlaying(true);
            player.setOverseas(i >= 10);
            player.setBackup(false);
            players.add(player);
        }
        return players;
    }

    private TeamEntity createSampleTeamEntity() {
        TeamEntity team = new TeamEntity(
                "Sample Team",
                "Sample Country",
                "Sample Captain",
                "Sample Coach",
                "Sample Owner",
                "sample-icon.png",
                1L,
                100
        );
        team.setId(1L);
        team.setPlayers(createSamplePlayerEntities());
        return team;
    }

    private List<PlayerEntity> createSamplePlayerEntities() {
        List<PlayerEntity> players = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            PlayerEntity player = new PlayerEntity();
            player.setId((long) i);
            player.setName("Player " + i);
            player.setEmail("player" + i + "@example.com");
            player.setUsername("player" + i);
            player.setDateOfBirth(LocalDate.of(1990, 1, 1));
            player.setSpecialization(i < 5 ? "Bowler" : (i < 10 ? "Batter" : "All-Rounder"));
            player.setGender("Male");
            player.setCountry(i < 10 ? "Sample Country" : "Other Country");
            player.setProfilePicture("player" + i + ".jpg");
            player.setPlaying(true);
            player.setOverseas(i >= 10);
            player.setBackup(false);
            players.add(player);
        }
        return players;
    }
}