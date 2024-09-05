package com.sam.profilecreation_service.controller;

import org.springframework.web.bind.annotation.RestController;
import com.sam.profilecreation_service.service.CoachService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sam.profilecreation_service.entity.CoachEntity;

import java.util.List;


@RestController
@RequestMapping("/api/coaches")
@CrossOrigin("http://localhost:3000")
public class CaochController {

    @Autowired
    private CoachService coachService;

    @PostMapping("/create")
    public ResponseEntity<CoachEntity> createCoach(@RequestBody CoachEntity CoachEntity) {
        CoachEntity createdCoach = coachService.createCoach(CoachEntity);
        return new ResponseEntity<>(createdCoach, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachEntity> findCoachById(@PathVariable Long id) {
        try {
            CoachEntity CoachEntity = coachService.findCoachById(id);
            return new ResponseEntity<>(CoachEntity, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<CoachEntity>> findAllCoaches() {
        List<CoachEntity> coaches = coachService.findAllCoaches();
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CoachEntity> updateCoach(@RequestBody CoachEntity CoachEntity, @PathVariable Long id) {
        try {
            CoachEntity updatedCoach = coachService.updateCoach(CoachEntity, id);
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
