package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.repository.CoachRepository;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.repository.TeamRepository;
import com.sam.profilecreation_service.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public TeamEntity createTeam(TeamEntity teamEntity) {
        // Validate the team entity for the required number of players
        validateTeamEntity(teamEntity);

        // Ensure players exist in the database and are available for assignment
        List<PlayerEntity> players = fetchExistingPlayers(teamEntity.getPlayers());

        // Assign the players to the team
        teamEntity.setPlayers(players);

        return teamRepository.save(teamEntity);
    }


    public boolean validateTeamCreation(List<PlayerEntity> players) {
        if (players.size() != 15) {
            return false; // Team must have exactly 15 players
        }

        // Count players by specialization
        Map<String, Long> specializationCounts = players.stream()
                .collect(Collectors.groupingBy(PlayerEntity::getSpecialization, Collectors.counting()));

        // Check specialization constraints
//        if (specializationCounts.getOrDefault("Batter", 0L) != 5 ||
//                specializationCounts.getOrDefault("Bowler", 0L) != 5 ||
//                specializationCounts.getOrDefault("All-Rounder", 0L) != 5) {
//            return false;
//        }

        // Count overseas, playing, and backup players
//        long overseasCount = players.stream().filter(PlayerEntity::isOverseas).count();
//        long playingCount = players.stream().filter(PlayerEntity::isPlaying).count();
//        long backupCount = players.stream().filter(PlayerEntity::isBackup).count();

        // Check overseas, playing, and backup constraints
//        if (overseasCount != 5 || playingCount != 11 || backupCount != 4) {
//            return false;
//        }

        return true;
    }
    public void saveTeam(TeamEntity teamEntity) {
       teamRepository.save(teamEntity);
    }

    @Override
    public TeamEntity getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
    }

    @Override
    public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public TeamEntity updateTeam(TeamEntity teamEntity, Long id) {
        TeamEntity existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));

        existingTeam.setName(teamEntity.getName());
        existingTeam.setCountry(teamEntity.getCountry());
        existingTeam.setTeamCaptain(teamEntity.getTeamCaptain());
        existingTeam.setOwner(teamEntity.getOwner());
        existingTeam.setIcon(teamEntity.getIcon());
        existingTeam.setTotalPoints(teamEntity.getTotalPoints());
        existingTeam.setLogo(teamEntity.getLogo());

        // If provided, update coach ID
        if (teamEntity.getCoachId() != null) {
            existingTeam.setCoachId(teamEntity.getCoachId());
        }

        // Validate and update players
        List<PlayerEntity> updatedPlayers = fetchExistingPlayers(teamEntity.getPlayers());
        existingTeam.setPlayers(updatedPlayers);

        return teamRepository.save(existingTeam);
    }

    @Override
    public void deleteTeam(Long id) {
        TeamEntity teamEntity = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
        teamRepository.delete(teamEntity);
    }

    @Override
    public void addPlayerToTeam(Long teamId, Long playerId) {
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));

        PlayerEntity playerEntity = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));

        // Check if the player is already part of a team
        if (playerEntity.getTeamid() != null) {
            throw new IllegalArgumentException("Player is already assigned to a team.");
        }

        playerEntity.setTeamid(teamEntity.getId()); // Assign player to this team
        teamEntity.getPlayers().add(playerEntity); // Add the player to the team's list

        teamRepository.save(teamEntity); // Save the changes
    }

    @Override
    public void validateTeamEntity(TeamEntity teamEntity) {
        List<PlayerEntity> players = teamEntity.getPlayers();

        // Validate the number of players
        if (players == null || players.size() != 15) {
            throw new IllegalArgumentException("A team must have exactly 15 players.");
        }

//        long overseasCount = players.stream().filter(PlayerEntity::isOverseas).count();
//        if (overseasCount != 5) {
//            throw new IllegalArgumentException("A team must have exactly 5 overseas players.");
//        }

//        List<PlayerEntity> playingPlayers = players.stream().filter(PlayerEntity::isPlaying).collect(Collectors.toList());
//        if (playingPlayers.size() != 11) {
//            throw new IllegalArgumentException("A team must have exactly 11 playing players.");
//        }
//
//        long overseasPlayingCount = playingPlayers.stream().filter(PlayerEntity::isOverseas).count();
//        if (overseasPlayingCount != 3) {
//            throw new IllegalArgumentException("There must be exactly 3 overseas players in the playing 11.");
//        }
//
//        // Ensure there are exactly 4 backup players
//        List<PlayerEntity> backupPlayers = players.stream().filter(PlayerEntity::isBackup).collect(Collectors.toList());
//        if (backupPlayers.size() != 4) {
//            throw new IllegalArgumentException("There must be exactly 4 backup players.");
//        }

        teamEntity.setPlayers(players);
        teamRepository.save(teamEntity);
    }

    private List<PlayerEntity> fetchExistingPlayers(List<PlayerEntity> players) {
        List<Long> playerIds = players.stream()
                .map(PlayerEntity::getId)
                .collect(Collectors.toList());

        return playerRepository.findAllById(playerIds);
    }
}
