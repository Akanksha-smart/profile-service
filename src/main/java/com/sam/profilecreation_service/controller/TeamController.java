package com.sam.profilecreation_service.controller;

import com.sam.profilecreation_service.dto.CoachDTO;
import com.sam.profilecreation_service.dto.PlayerDTO;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import org.springframework.web.bind.annotation.RestController;
import com.sam.profilecreation_service.dto.TeamDTO;
import com.sam.profilecreation_service.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {


        @Autowired
        private TeamService teamService;

//    @PostMapping("/create")
//    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
////        if (teamDTO.getPlayers() == null) {
////            teamDTO.setPlayers(new ArrayList<>()); // Initialize players list if null
////        }
////
////        TeamDTO createdTeam = teamService.createTeam(teamDTO);
////        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
//
//        TeamEntity teamEntity = convertToTeamEntity(teamDTO);
//
//        // Fetch existing players from the database
//        List<PlayerEntity> existingPlayers = playerRepository.findAllById(teamDTO.getPlayerIds());
//
//        // Set the fetched players to the team
//        teamEntity.setPlayers(existingPlayers);
//
//        // Save the team entity
//        TeamEntity savedTeamEntity = teamRepository.save(teamEntity);
//
//        // Convert Entity back to DTO
//        return convertToTeamDTO(savedTeamEntity);
//    }

        @GetMapping("/{id}")
        public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
            try {
                TeamDTO teamDTO = teamService.getTeamById(id);
                return new ResponseEntity<>(teamDTO, HttpStatus.OK);
            } catch (EntityNotFoundException ex) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }


        @GetMapping
        public ResponseEntity<List<TeamDTO>> getAllTeams() {
            List<TeamDTO> teamList = teamService.getAllTeams();
            return new ResponseEntity<>(teamList, HttpStatus.OK);
        }


        @PutMapping("/update/{id}")
        public ResponseEntity<TeamDTO> updateTeam(@RequestBody TeamDTO team, @PathVariable Long id) {
            try {
                TeamDTO updatedTeam = teamService.updateTeam(team, id);
                return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
            } catch (EntityNotFoundException ex) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
            try {
                teamService.deleteTeam(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (EntityNotFoundException ex) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

    @PostMapping("/{teamId}/players")
    public ResponseEntity<Void> addPlayerToTeam(@PathVariable("teamId") Long teamId, @RequestBody PlayerDTO playerDTO) {
        teamService.addPlayerToTeam(teamId, playerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{teamId}/coach")
    public ResponseEntity<Void> addCoachToTeam(@PathVariable("teamId") Long teamId, @RequestBody CoachDTO coachDTO) {
        teamService.addCoachToTeam(teamId, coachDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
