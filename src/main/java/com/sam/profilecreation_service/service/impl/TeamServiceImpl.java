package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.repository.CoachRepository;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.repository.TeamRepository;
import com.sam.profilecreation_service.service.CoachService;
import com.sam.profilecreation_service.service.PlayerService;
import com.sam.profilecreation_service.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {


//    @Transactional
//    public TeamEntity createTeam(TeamEntity TeamEntity) {
//         validateTeamEntity(TeamEntity);
//
//        List<PlayerEntity> existingPlayers = fetchExistingPlayers(TeamEntity.getPlayerIds());
//        teamEntity.setPlayers(existingPlayers);
//        assignPlayingAndBackupRoles(existingPlayers, TeamEntity.getPlayingPlayerIds());
//        TeamEntity savedTeamEntity = teamRepository.save(teamEntity);
//        return convertToTeamEntity(savedTeamEntity);
//    }
//
//
//
//    @Override
//    public TeamEntity getTeamById(Long id) {
//        TeamEntity teamEntity = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
//        return convertToTeamEntity(teamEntity);
//    }
//
//    @Override
//    public List<TeamEntity> getAllTeams() {
//        List<TeamEntity> teamEntities = teamRepository.findAll();
//        return teamEntities.stream()
//                .map(this::convertToTeamEntity)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public TeamEntity updateTeam(TeamEntity TeamEntity, Long id) {
//        TeamEntity teamEntity = teamRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
//
//        teamEntity.setName(TeamEntity.getName());
//        teamEntity.setCountry(TeamEntity.getCountry());
//        teamEntity.setTeamCaptain(TeamEntity.getTeamCaptain());
//        teamEntity.setOwner(TeamEntity.getOwner());
//        teamEntity.setIcon(TeamEntity.getIcon());
//        teamEntity.setTotalPoints(TeamEntity.getTotalPoints());
//        teamEntity.setLogo(TeamEntity.getLogo()); // Uncomment this line if you want to update the logo
//
//        if (TeamEntity.getCoachId() != null) {
//            teamEntity.setCoachId(TeamEntity.getCoachId());
//        }
//        List<Long> playerIds = teamEntity.getPlayers().stream()
//                .map(PlayerEntity::getId)
//                .collect(Collectors.toList());
//        TeamEntity.setPlayerIds(playerIds);
//
//        TeamEntity updatedTeamEntity = teamRepository.save(teamEntity);
//        return convertToTeamEntity(updatedTeamEntity);
//    }
//
//
//
//    @Override
//    public void deleteTeam(Long id) {
//
//        Optional<TeamEntity> teamEntityOptional = teamRepository.findById(id);
//        if (teamEntityOptional.isPresent()) {
//            teamRepository.delete(teamEntityOptional.get());
//        }
//        else
//            throw new EntityNotFoundException("Team not found with id: " + id);
//    }

//    @Override
//    public void addPlayerToTeam(Long teamId, PlayerDTO playerDTO) {
//        TeamEntity teamEntity = teamRepository.findById(teamId)
//                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));
//
//        PlayerEntity playerEntity = playerService.convertToPlayerEntity(playerDTO);
//        playerEntity.setTeamid(teamEntity.getId()); // Setting the team in player entity
//
//        teamEntity.getPlayers().add(playerEntity); // Adding player to team's list of players
//        teamRepository.save(teamEntity);
//    }


//    @Override
//    public void addCoachToTeam(Long teamId, CoachDTO coachDTO) {
//        TeamEntity teamEntity = teamRepository.findById(teamId)
//                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));
//
//        CoachEntity coachEntity = coachService.convertToCoachEntity(coachDTO);
//        coachEntity.setTeamId(teamEntity.getCoachId());
//        teamEntity.setCoachId(coachEntity.getTeamId());
//
//        coachRepository.save(coachEntity);
//        teamRepository.save(teamEntity);
//    }

//    @Override
//    public void validateTeamEntity(TeamEntity TeamEntity) {
//        List<Long> playerIds = TeamEntity.getPlayerIds();
//
//        if (playerIds == null||playerIds.size() != 15) {
//            throw new IllegalArgumentException("A team must have exactly 15 players.");
//        }
//
//        List<PlayerEntity> players = playerRepository.findAllById(TeamEntity.getPlayerIds());
//
//        if (players.size() != 15) {
//            throw new IllegalArgumentException("Some players were not found in the database.");
//        }
//        // Count overseas players
//        long overseasCount = players.stream()
//                .filter(PlayerEntity::isOverseas)
//                .count();
//
//         //Check if there are exactly 5 overseas players
//        if (overseasCount != 5) {
//            throw new IllegalArgumentException("There must be exactly 5 overseas players in the team.");
//        }
//
//        // Check playing and backup players
//        List<PlayerEntity> playingPlayers = players.stream()
//                .filter(PlayerEntity::isPlaying)
//                .toList();
//        List<PlayerEntity> backupPlayers = players.stream()
//                .filter(player -> !player.isPlaying())
//                .toList();
//         //Check if there are exactly 11 playing players and 4 backup players
//        if (playingPlayers.size() != 11) {
//            throw new IllegalArgumentException("There must be exactly 11 playing players.");
//        }
//        if (backupPlayers.size() != 4) {
//            throw new IllegalArgumentException("There must be exactly 4 backup players.");
//        }
//
//         //Check if there are 3 overseas players in the playing team
//        long overseasPlayingCount = playingPlayers.stream()
//                .filter(PlayerEntity::isOverseas)
//                .count();
//        if (overseasPlayingCount != 3) {
//            throw new IllegalArgumentException("There must be exactly 3 overseas players in the playing team.");
//        }
//    }
//
//    public List<PlayerEntity> fetchExistingPlayers(List<Long> playerIds) {
//        if (playerIds == null || playerIds.isEmpty()) {
//            return new ArrayList<>(); // Return an empty list if no player IDs are provided
//        }
//        return playerRepository.findAllById(playerIds);
//    }
//
//    private void assignPlayingAndBackupRoles(List<PlayerEntity> players, List<Long> playingPlayerIds) {
//        // Convert playingPlayerIds to a set for quick lookup
//        Set<Long> playingIdsSet = new HashSet<>(playingPlayerIds);
//
//        // Loop through all players and assign their roles
//        for (PlayerEntity player : players) {
//            if (playingIdsSet.contains(player.getId())) {
//                player.setPlaying(true); // Mark as playing if in the list
//            } else {
//                player.setPlaying(false); // Otherwise, mark as backup
//            }
//        }
//
//    }
}
