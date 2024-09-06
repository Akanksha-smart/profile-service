package com.sam.profilecreation_service.controller;

import com.sam.profilecreation_service.entity.PlayerEntity;
import org.springframework.web.bind.annotation.RestController;
import com.sam.profilecreation_service.service.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin("http://localhost:3000")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/create")
    public ResponseEntity<PlayerEntity> createPlayer(@RequestBody PlayerEntity playerDTO) {
        PlayerEntity createdPlayer = playerService.createPlayer(playerDTO);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerEntity> getPlayerById(@PathVariable Long id) {
        try {
            PlayerEntity PlayerEntity= playerService.getPlayerById(id);
            return new ResponseEntity<>( PlayerEntity, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<PlayerEntity>> getAllPlayers() {
        List<PlayerEntity> players = playerService.getAllPlayers();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PlayerEntity> updatePlayer(@RequestBody PlayerEntity playerDTO, @PathVariable Long id) {
        try {
            PlayerEntity updatedPlayer = playerService.updatePlayer(playerDTO, id);
            return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        try {
            playerService.deletePlayer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/country/{country}")
    public List<PlayerEntity> getPlayersByCountry(@PathVariable String country) {
        return playerService.getPlayersByCountry(country);
    }

    @GetMapping("/no-team")
    public ResponseEntity<List<PlayerEntity>> getPlayersWithNoTeam() {
        List<PlayerEntity> playersWithoutTeam = playerService.getPlayersWithNoTeam();
        return new ResponseEntity<>(playersWithoutTeam, HttpStatus.OK);
    }

}
