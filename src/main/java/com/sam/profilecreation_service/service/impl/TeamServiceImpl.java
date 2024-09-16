package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.dto.PlayerDTO;
import com.sam.profilecreation_service.dto.TeamRegisterDTO;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.exception.TeamNotFoundException;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.repository.TeamRepository;
import com.sam.profilecreation_service.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public TeamEntity createTeam(TeamRegisterDTO teamRegisterDTO) throws Exception {
        List<PlayerEntity> players = convertPlayerDtoToEntity(teamRegisterDTO.getPlayers());
        validateTeamPlayers(players, teamRegisterDTO.getCountry());

        TeamEntity team = new TeamEntity(
                teamRegisterDTO.getName(),
                teamRegisterDTO.getCountry(),
                teamRegisterDTO.getTeamCaptain(),
                teamRegisterDTO.getCoachName(),
                teamRegisterDTO.getOwner(),
                teamRegisterDTO.getIcon(),
                teamRegisterDTO.getCoachId(),
                teamRegisterDTO.getTotalPoints()
        );

        TeamEntity savedTeam = teamRepository.save(team);

        players.forEach(player -> {
            player.setTeamEntity(savedTeam);
            player.setTeamid(savedTeam.getId());
        });

        List<PlayerEntity> managedPlayers = savePlayerOrUpdatePlayers(players);
        savedTeam.setPlayers(managedPlayers);
        return teamRepository.save(savedTeam);
    }

    private List<PlayerEntity> savePlayerOrUpdatePlayers(List<PlayerEntity> players) {
        return players.stream()
                .map(player -> player.getId() != null ? updateExistingPlayer(player) : playerRepository.save(player))
                .collect(Collectors.toList());
    }

    private PlayerEntity updateExistingPlayer(PlayerEntity player) {
        return playerRepository.findById(player.getId())
                .map(existingPlayer -> updatePlayerDetails(existingPlayer, player))
                .orElse(playerRepository.save(player));
    }

    private PlayerEntity updatePlayerDetails(PlayerEntity existingPlayer, PlayerEntity newPlayer) {
        existingPlayer.setName(newPlayer.getName());
        existingPlayer.setEmail(newPlayer.getEmail());
        existingPlayer.setUsername(newPlayer.getUsername());
        existingPlayer.setDateOfBirth(newPlayer.getDateOfBirth());
        existingPlayer.setSpecialization(newPlayer.getSpecialization());
        existingPlayer.setGender(newPlayer.getGender());
        existingPlayer.setCountry(newPlayer.getCountry());
        existingPlayer.setProfilePicture(newPlayer.getProfilePicture());
        existingPlayer.setTeamid(newPlayer.getTeamid());
        existingPlayer.setPlaying(newPlayer.isPlaying());
        existingPlayer.setOverseas(newPlayer.isOverseas());
        existingPlayer.setBackup(newPlayer.isBackup());
        return playerRepository.save(existingPlayer);
    }

    private List<PlayerEntity> convertPlayerDtoToEntity(List<PlayerDTO> playerDTOs) {
        return playerDTOs.stream().map(dto -> {
            PlayerEntity player = new PlayerEntity();
            player.setId(dto.getId());
            player.setName(dto.getName());
            player.setEmail(dto.getEmail());
            player.setUsername(dto.getUsername());
            player.setDateOfBirth(dto.getDateOfBirth());
            player.setSpecialization(dto.getSpecialization());
            player.setGender(dto.getGender());
            player.setCountry(dto.getCountry());
            player.setProfilePicture(dto.getProfilePicture());
            player.setPlaying(dto.getPlaying());
            player.setOverseas(dto.getOverseas());
            player.setBackup(dto.getBackup());
            return player;
        }).collect(Collectors.toList());
    }

    private void validateTeamPlayers(List<PlayerEntity> players, String teamCountry) throws Exception {
        if (players.size() != 15) throw new Exception("A team must consist of exactly 15 players.");

        long bowlers = countBySpecialization(players, "Bowler");
        long batters = countBySpecialization(players, "Batter");
        long allRounders = countBySpecialization(players, "All-Rounder");

        if (bowlers != 5 || batters != 5 || allRounders != 5)
            throw new Exception("Team must have 5 Bowlers, 5 Batters, and 5 All-Rounders.");

        long localPlayers = players.stream().filter(p -> teamCountry.equalsIgnoreCase(p.getCountry())).count();
        if (localPlayers < 10) throw new Exception("At least 10 players must be from the team's country.");
        if (players.size() - localPlayers < 5) throw new Exception("At least 5 players must be from other countries.");
    }

    private long countBySpecialization(List<PlayerEntity> players, String specialization) {
        return players.stream().filter(p -> specialization.equalsIgnoreCase(p.getSpecialization())).count();
    }

    @Override
    public TeamEntity getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
    }

    @Override
    public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public TeamEntity updateTeam(TeamEntity teamEntity, Long id) {
        TeamEntity existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));

        updateTeamDetails(existingTeam, teamEntity);

        List<PlayerEntity> updatedPlayers = fetchExistingPlayers(teamEntity.getPlayers());
        existingTeam.setPlayers(updatedPlayers);

        return teamRepository.save(existingTeam);
    }

    private void updateTeamDetails(TeamEntity existingTeam, TeamEntity newTeam) {
        existingTeam.setName(newTeam.getName());
        existingTeam.setCountry(newTeam.getCountry());
        existingTeam.setTeamCaptain(newTeam.getTeamCaptain());
        existingTeam.setOwner(newTeam.getOwner());
        existingTeam.setIcon(newTeam.getIcon());
        existingTeam.setTotalPoints(newTeam.getTotalPoints());
        existingTeam.setCoachId(newTeam.getCoachId());
    }

    @Override
    public void deleteTeam(Long id) {
        TeamEntity teamEntity = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
        teamRepository.delete(teamEntity);
    }

    @Override
    public void addPlayerToTeam(Long teamId, Long playerId) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));

        PlayerEntity player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));

        if (player.getTeamid() != null) throw new IllegalArgumentException("Player is already assigned to a team.");

        player.setTeamid(team.getId());
        team.getPlayers().add(player);

        teamRepository.save(team);
    }

    @Override
    public Integer getTeamsByCoachName(String name) {
        try {
            Integer teamCount = teamRepository.findByCoachName(name);

            if (teamCount == null) {
                throw new TeamNotFoundException("No teams found for coach: " + name);
            }

            return teamCount;
        } catch (TeamNotFoundException e) {
            throw e;
        } catch (Exception e) {

            throw new ServiceException("An error occurred while fetching teams for coach: " + name, e);
        }
    }


    private List<PlayerEntity> fetchExistingPlayers(List<PlayerEntity> players) {
        List<Long> playerIds = players.stream().map(PlayerEntity::getId).collect(Collectors.toList());
        return playerRepository.findAllById(playerIds);
    }
}
