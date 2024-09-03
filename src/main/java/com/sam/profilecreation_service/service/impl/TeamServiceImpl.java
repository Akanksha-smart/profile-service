package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.dto.CoachDTO;
import com.sam.profilecreation_service.dto.PlayerDTO;
import com.sam.profilecreation_service.dto.TeamDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {


    @Autowired
    private  TeamRepository teamRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    @Lazy
    private PlayerService playerService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    private TeamDTO convertToTeamDTO(TeamEntity teamEntity) {
//        List<PlayerDTO> playerDTOs = teamEntity.getPlayers().stream()
//                .map(playerService::convertToPlayerDTO)
//                .collect(Collectors.toList());
//
//        CoachDTO coachDTO = teamEntity.getCoachId() != null ? coachService.findCoachById(teamEntity.getCoachId()) : null;
//
//        return new TeamDTO(
//                teamEntity.getId(),
//                teamEntity.getName(),
//                coachDTO,
//                teamEntity.getCountry(),
//                teamEntity.getTeamCaptain(),
//                teamEntity.getOwner(),
//                teamEntity.getIcon(),
//                teamEntity.getTotalPoints(),
//                playerDTOs
//        );
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(teamEntity.getId());
        teamDTO.setName(teamEntity.getName());
        teamDTO.setCountry(teamEntity.getCountry());
        teamDTO.setTeamCaptain(teamEntity.getTeamCaptain());
        teamDTO.setIcon(teamEntity.getIcon());
        teamDTO.setOwner(teamEntity.getOwner());
//        teamDTO.setPlayers(teamEntity.getPlayers());
        teamDTO.setTotalPoints(teamEntity.getTotalPoints());
        teamDTO.setCoachId(teamEntity.getCoachId());
        teamEntity.setPlayers(new ArrayList<>());

        return teamDTO;
    }


    private TeamEntity convertToTeamEntity(TeamDTO teamDTO) {
        TeamEntity teamEntity = new TeamEntity();

        teamEntity.setId(teamDTO.getId());
        teamEntity.setName(teamDTO.getName());
        teamEntity.setCountry(teamDTO.getCountry());
        teamEntity.setTeamCaptain(teamDTO.getTeamCaptain());
        teamEntity.setOwner(teamDTO.getOwner());
        teamEntity.setIcon(teamDTO.getIcon());
        teamEntity.setTotalPoints(teamDTO.getTotalPoints());

        if (teamDTO.getCoachId() != null) {
            teamEntity.setCoachId(teamDTO.getCoachId());
        }
        List<Long> playerIds = teamEntity.getPlayers().stream()
                .map(PlayerEntity::getId)
                .collect(Collectors.toList());
        teamDTO.setPlayerIds(playerIds);


        return teamEntity;
    }

    @Transactional
    public TeamDTO createTeam(TeamDTO teamDTO) {
//        validateTeamDTO(teamDTO);
        TeamEntity teamEntity = convertToTeamEntity(teamDTO);

        // Fetch existing players from the database
       // List<PlayerEntity> players = playerRepository.findAllById(teamDTO.getPlayers());
        //teamEntity.setPlayers(players);

        List<PlayerEntity> playerEntities = playerRepository.findAllById(teamDTO.getPlayerIds());
        teamEntity.setPlayers(playerEntities);

        // Save the team entity
        TeamEntity savedTeamEntity = teamRepository.save(teamEntity);

        // Convert Entity back to DTO
        return convertToTeamDTO(savedTeamEntity);

    }

    @Override
    public TeamDTO getTeamById(Long id) {
        TeamEntity teamEntity = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
        return convertToTeamDTO(teamEntity);
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        List<TeamEntity> teamEntities = teamRepository.findAll();
        return teamEntities.stream()
                .map(this::convertToTeamDTO)
                .collect(Collectors.toList());
    }

//    @Override
//    public TeamDTO updateTeam(TeamDTO teamDTO, Long id) {
//        TeamEntity teamEntity = teamRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
//
//        teamEntity.setName(teamDTO.getName());
//        teamEntity.setCountry(teamDTO.getCountry());
//        teamEntity.setTeamCaptain(teamDTO.getTeamCaptain());
//        teamEntity.setOwner(teamDTO.getOwner());
//        teamEntity.setIcon(teamDTO.getIcon());
//        teamEntity.setTotalPoints(teamDTO.getTotalPoints());
//
//        // Convert and set Coach
//        if (teamDTO.getCoach() != null) {
//            CoachDTO coachDTO = coachService.convertToCoachDTO(teamDTO.getCoach());
//            teamEntity.setCoach(coachService.convertToCoachEntity(coachDTO));
//        }
//
//        // Handle Players
//        if (teamDTO.getPlayers() != null) {
//            List<PlayerEntity> players = teamDTO.getPlayers().stream()
//                    .map(playerDTO -> playerService.convertToPlayerDTO(playerDTO))
//                    .collect(Collectors.toList());
//            teamEntity.setPlayers(players);
//        }
//
//        TeamEntity updatedTeamEntity = teamRepository.save(teamEntity);
//        return convertToTeamDTO(updatedTeamEntity);
//    }

    @Override
    public TeamDTO updateTeam(TeamDTO teamDTO, Long id) {
        // Find the existing team entity by ID, or throw an exception if not found
        TeamEntity teamEntity = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));

        // Update the team entity's fields with the new values from teamDTO
        teamEntity.setName(teamDTO.getName());
        teamEntity.setCountry(teamDTO.getCountry());
        teamEntity.setTeamCaptain(teamDTO.getTeamCaptain());
        teamEntity.setOwner(teamDTO.getOwner());
        teamEntity.setIcon(teamDTO.getIcon());
        teamEntity.setTotalPoints(teamDTO.getTotalPoints());
        // teamEntity.setLogo(teamDTO.getLogo()); // Uncomment this line if you want to update the logo

        if (teamDTO.getCoachId() != null) {
            teamEntity.setCoachId(teamDTO.getCoachId());
        }
        List<Long> playerIds = teamEntity.getPlayers().stream()
                .map(PlayerEntity::getId)
                .collect(Collectors.toList());
        teamDTO.setPlayerIds(playerIds);

        TeamEntity updatedTeamEntity = teamRepository.save(teamEntity);
        return convertToTeamDTO(updatedTeamEntity);
    }



    @Override
    public void deleteTeam(Long id) {

        Optional<TeamEntity> teamEntityOptional = teamRepository.findById(id);
        if (teamEntityOptional.isPresent()) {
            teamRepository.delete(teamEntityOptional.get());
        }
        else
            throw new EntityNotFoundException("Team not found with id: " + id);
    }
//
//    @Override
//    public void addPlayerToTeam(Long teamId, PlayerDTO playerDTO) {
//        TeamEntity team = teamRepository.findById(teamId)
//                .orElseThrow(() -> new RuntimeException("Team not found with id " + teamId));
//        PlayerEntity player = playerServiceImpl.convertToPlayerEntity(playerDTO);
//        player.setTeam(team);
//        team.getPlayers().add(player);
//        playerRepository.save(player);
//        teamRepository.save(team);
//    }

    @Override
    public void addPlayerToTeam(Long teamId, PlayerDTO playerDTO) {
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));

        PlayerEntity playerEntity = playerService.convertToPlayerEntity(playerDTO);
        playerEntity.setTeamid(teamEntity.getId()); // Setting the team in player entity

        teamEntity.getPlayers().add(playerEntity); // Adding player to team's list of players
        teamRepository.save(teamEntity);
    }


    @Override
    public void addCoachToTeam(Long teamId, CoachDTO coachDTO) {
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));

        CoachEntity coachEntity = coachService.convertToCoachEntity(coachDTO);
        coachEntity.setTeamId(teamEntity.getCoachId());
        teamEntity.setCoachId(coachEntity.getTeamId());

        coachRepository.save(coachEntity);
        teamRepository.save(teamEntity);
    }

//    @Override
//    public void validateTeamDTO(TeamDTO teamDTO) {
//        List<PlayerEntity> players = teamDTO.getPlayers();
////
////        if (players == null) {
////            throw new IllegalArgumentException("Players list cannot be null.");
////        }
//
////        // Check if there are exactly 15 players
////        if (players == null||players.size() != 15) {
////            throw new IllegalArgumentException("A team must have exactly 15 players.");
////        }
//
//        if (teamDTO.getPlayers() == null || teamDTO.getPlayers().size() != 15) {
//            throw new IllegalArgumentException("A team must have exactly 15 players.");
//        }
//
//        // Count overseas players
////        long overseasCount = players.stream()
////                .filter(PlayerEntity::isOverseas)
////                .count();
//
//        // Check if there are exactly 5 overseas players
////        if (overseasCount != 5) {
////            throw new IllegalArgumentException("There must be exactly 5 overseas players in the team.");
////        }
//
//        // Check playing and backup players
//        List<PlayerEntity> playingPlayers = players.stream()
//                .filter(PlayerEntity::isPlaying)
//                .toList();
//        List<PlayerEntity> backupPlayers = players.stream()
//                .filter(player -> !player.isPlaying())
//                .toList();
//        // Check if there are exactly 11 playing players and 4 backup players
////        if (playingPlayers.size() != 11) {
////            throw new IllegalArgumentException("There must be exactly 11 playing players.");
////        }
////        if (backupPlayers.size() != 4) {
////            throw new IllegalArgumentException("There must be exactly 4 backup players.");
////        }
//
//        // Check if there are 3 overseas players in the playing team
////        long overseasPlayingCount = playingPlayers.stream()
////                .filter(PlayerEntity::isOverseas)
////                .count();
////        if (overseasPlayingCount != 3) {
////            throw new IllegalArgumentException("There must be exactly 3 overseas players in the playing team.");
////        }
//    }

}
