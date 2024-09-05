package com.sam.profilecreation_service.service;


import com.sam.profilecreation_service.dto.TeamRegisterDTO;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TeamService {

    @Transactional
    TeamEntity createTeam(TeamRegisterDTO teamRegisterDTO) throws Exception;

//     boolean validateTeamCreation(List<PlayerEntity> players);
//
//    TeamEntity getTeamById(Long id);
//
//    void saveTeam(TeamEntity teamEntity);

    TeamEntity getTeamById(Long id);

    List<TeamEntity> getAllTeams();

    TeamEntity updateTeam(TeamEntity teamEntity, Long id);

    void deleteTeam(Long id);

    void addPlayerToTeam(Long teamId, Long playerId);

   // void addCoachToTeam(Long teamId, Long coachId);

    void validateTeamEntity(TeamEntity teamEntity);

//    TeamEntity createTeam(TeamEntity TeamEntity);
//
//    TeamEntity getTeamById(Long id);
//    List<TeamEntity> getAllTeams();
//
//    TeamEntity updateTeam(TeamEntity team , Long id);
//
//    void deleteTeam(Long id);
//    void addPlayerToTeam(Long teamId, PlayerDTO playerDTO);
//    void addCoachToTeam(Long teamId, CoachDTO coachDTO);
//
//    void validateTeamEntity(TeamEntity TeamEntity);
//}
}
