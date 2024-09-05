package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.dto.PlayerDTO;
import com.sam.profilecreation_service.dto.TeamRegisterDTO;
import com.sam.profilecreation_service.entity.ERole;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.repository.TeamRepository;
import com.sam.profilecreation_service.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public TeamEntity createTeam(TeamRegisterDTO teamRegisterDTO) throws Exception {
        // Convert PlayerDTOs to Player entities
        List<PlayerEntity> players = convertPlayerDTOsToEntities(teamRegisterDTO.getPlayers());

        // Validate players according to the rules
        validatePlayers(players, teamRegisterDTO.getCountry());

        // Create a new Team entity and populate it with DTO data
        TeamEntity team = new TeamEntity();
        team.setName(teamRegisterDTO.getName());
        team.setCountry(teamRegisterDTO.getCountry());
        team.setTeamCaptain(teamRegisterDTO.getTeamCaptain());
        team.setCoachName(teamRegisterDTO.getCoach());
        team.setOwner(teamRegisterDTO.getOwner());
        team.setIcon(teamRegisterDTO.getIcon());
        team.setTotalPoints(teamRegisterDTO.getTotalPoints());
        team.setPlayers(players); // Set the list of players

        // Save or update players
        List<PlayerEntity> managedPlayers = saveOrUpdatePlayers(players);

        // Associate managed players with the team
        team.setPlayers(managedPlayers);

        // Save the team
        return teamRepository.save(team);
    }

    private List<PlayerEntity> saveOrUpdatePlayers(List<PlayerEntity> players) {
        List<PlayerEntity> managedPlayers = new ArrayList<>();
        for (PlayerEntity player : players) {
            if (player.getId() != null) {
                // If player ID exists, it is a detached entity, so we merge it
                PlayerEntity managedPlayer = playerRepository.findById(player.getId()).orElse(null);
                if (managedPlayer != null) {
                    // Update existing managed player
                    managedPlayer.setName(player.getName());
                    managedPlayer.setEmail(player.getEmail());
                    managedPlayer.setUsername(player.getUsername());
                    managedPlayer.setDateOfBirth(player.getDateOfBirth());
                    managedPlayer.setSpecialization(player.getSpecialization());
                    managedPlayer.setGender(player.getGender());
                    managedPlayer.setCountry(player.getCountry());
                    managedPlayer.setProfilePicture(player.getProfilePicture());
                    managedPlayer.setRole(player.getRole());
                    managedPlayer.setPlaying(player.isPlaying());
                    managedPlayer.setOverseas(player.isOverseas());
                    managedPlayer.setBackup(player.isBackup());
                    managedPlayers.add(playerRepository.save(managedPlayer)); // Save the updated player
                } else {
                    // Handle case where player with ID does not exist
                    managedPlayers.add(playerRepository.save(player));
                }
            } else {
                // New player, so we persist
                managedPlayers.add(playerRepository.save(player));
            }
        }
        return managedPlayers;
    }

    private List<PlayerEntity> convertPlayerDTOsToEntities(List<PlayerDTO> playerDTOs) throws Exception {
        List<PlayerEntity> players = new ArrayList<>();
        for (PlayerDTO dto : playerDTOs) {
            PlayerEntity player = new PlayerEntity();
            player.setId(dto.getId()); // Set the ID which is used for detecting if it's a detached entity
            player.setName(dto.getName());
            player.setEmail(dto.getEmail());
            player.setUsername(dto.getUsername());
            player.setDateOfBirth(dto.getDateOfBirth());
            player.setSpecialization(dto.getSpecialization());
            player.setGender(dto.getGender());
            player.setCountry(dto.getCountry());
            player.setProfilePicture(dto.getProfilePicture());
            player.setRole(ERole.valueOf(dto.getRole()));
            player.setPlaying(dto.getPlaying());
            player.setOverseas(dto.getOverseas());
            player.setBackup(dto.getBackup());
            players.add(player);
        }
        return players;
    }

    private void validatePlayers(List<PlayerEntity> players, String teamCountry) throws Exception {
        if (players.size() != 15) {
            throw new Exception("A team must consist of exactly 15 players.");
        }

        // Count specializations
        long bowlers = players.stream().filter(p -> "Bowler".equals(p.getSpecialization())).count();
        long batters = players.stream().filter(p -> "Batter".equals(p.getSpecialization())).count();
        long allRounders = players.stream().filter(p -> "All-Rounder".equals(p.getSpecialization())).count();

        if (bowlers != 5 || batters != 5 || allRounders != 5) {
            throw new Exception("Team must have 5 Bowlers, 5 Batters, and 5 All-Rounders.");
        }

        // Check country rules
        long sameCountryCount = players.stream().filter(p -> teamCountry.equals(p.getCountry())).count();
        if (sameCountryCount < 10) {
            throw new Exception("The team must have at least 10 players from the same country as the team's country.");
        }

        // Ensure 5 players from other countries
        long otherCountriesCount = players.size() - sameCountryCount;
        if (otherCountriesCount < 5) {
            throw new Exception("The team must have at least 5 players from countries other than the team's country.");
        }
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
