package com.sam.profilecreation_service.controller;

import org.springframework.web.bind.annotation.RestController;
import com.sam.profilecreation_service.dto.CoachDTO;
import com.sam.profilecreation_service.service.CoachService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/coaches")
public class CaochController {

    @Autowired
    private CoachService coachService;

    @PostMapping("/create")
    public ResponseEntity<CoachDTO> createCoach(@RequestBody CoachDTO coachDTO) {
        CoachDTO createdCoach = coachService.createCoach(coachDTO);
        return new ResponseEntity<>(createdCoach, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachDTO> findCoachById(@PathVariable Long id) {
        try {
            CoachDTO coachDTO = coachService.findCoachById(Math.toIntExact(id));
            return new ResponseEntity<>(coachDTO, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<CoachDTO>> findAllCoaches() {
        List<CoachDTO> coaches = coachService.findAllCoaches();
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CoachDTO> updateCoach(@RequestBody CoachDTO coachDTO, @PathVariable Long id) {
        try {
            CoachDTO updatedCoach = coachService.updateCoach(coachDTO, id);
            return new ResponseEntity<>(updatedCoach, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        try {
            coachService.deleteCoach(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
