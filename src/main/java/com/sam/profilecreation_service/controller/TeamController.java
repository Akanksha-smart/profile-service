package com.sam.profilecreation_service.controller;

import com.sam.profilecreation_service.dto.TeamRegisterDTO;
import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin("http://localhost:3000")
public class TeamController {

    @Autowired
    private TeamService teamService;




    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody TeamRegisterDTO teamRegisterDTO) {
        try {
            TeamEntity team = teamService.createTeam(teamRegisterDTO);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        try {
            TeamEntity teamEntity = teamService.getTeamById(id);
            return new ResponseEntity<>(teamEntity, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Team not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TeamEntity>> getAllTeams() {
        List<TeamEntity> teamList = teamService.getAllTeams();
        return new ResponseEntity<>(teamList, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTeam(@RequestBody TeamEntity teamEntity, @PathVariable Long id) {
        try {
            teamService.updateTeam(teamEntity, id);
            return new ResponseEntity<>("Team updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Team not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the team.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return new ResponseEntity<>("Team deleted successfully", HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Team not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{teamId}/players")
    public ResponseEntity<String> addPlayerToTeam(@PathVariable("teamId") Long teamId, @RequestBody PlayerEntity playerEntity) {
        try {
            teamService.addPlayerToTeam(teamId, playerEntity.getId());
            return new ResponseEntity<>("Player added to team successfully", HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Team not found with ID: " + teamId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the player to the team.");
        }
    }
//
//    @PostMapping("/{teamId}/coach")
//    public ResponseEntity<String> addCoachToTeam(@PathVariable("teamId") Long teamId, @RequestBody CoachEntity coachEntity) {
//        try {
//            teamService.addCoachToTeam(teamId, coachEntity.getCoachId());
//            return new ResponseEntity<>("Coach added to team successfully", HttpStatus.OK);
//        } catch (EntityNotFoundException ex) {
//            return new ResponseEntity<>("Team not found with ID: " + teamId, HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the coach to the team.");
//        }
//    }
}
