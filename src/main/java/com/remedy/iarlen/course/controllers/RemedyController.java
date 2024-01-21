package com.remedy.iarlen.course.controllers;

import com.remedy.iarlen.course.Remedy.RemedyWithIdDTO;
import com.remedy.iarlen.course.Remedy.RemedyDTO;
import com.remedy.iarlen.course.models.RemedyModel;
import com.remedy.iarlen.course.repositories.RemedyRepository;
import com.remedy.iarlen.course.services.RemedyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/remedy")
public class RemedyController {
    @Autowired
    RemedyService remedyService;

    @Autowired
    private RemedyRepository remedyRepository;

    @Operation(summary = "This method is used to create a remedy.")
    @PostMapping
    public ResponseEntity<RemedyWithIdDTO> createRemedy(@RequestBody @Valid RemedyDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(remedyService.createRemedy(data));
    }

    @Operation(summary = "This method is used to get the remedy.")
    @Transactional
    @GetMapping
    public ResponseEntity<List<RemedyWithIdDTO>> allRemedy() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(remedyService.listAllRemedy());
    }

    @Operation(summary = "This method is used to get a remedy by id.")
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<RemedyWithIdDTO> getRemedyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(remedyService.getRemedyById(id));
    }

    @Operation(summary = "This method is used to update a remedy by id.")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<RemedyWithIdDTO> updateRemedyById(
            @PathVariable Long id, @RequestBody @Valid RemedyDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(remedyService.updateRemedyById(id, data));
    }

    @Operation(summary = "This method is used to delete a remedy by id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<RemedyWithIdDTO> deleteRemedyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(remedyService.deleteRemedyById(id));
    }

    @Operation(summary = "This method is used to disable a remedy by id.")
    @Transactional
    @DeleteMapping("disable/{id}")
    public ResponseEntity<RemedyWithIdDTO> disableRemedyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(remedyService.disableRemedyById(id));
    }


    @Operation(summary = "This method is used to enable a remedy by id.")
    @Transactional
    @DeleteMapping("enable/{id}")
    public ResponseEntity<RemedyWithIdDTO> enableRemedyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(remedyService.enableRemedyById(id));
    }
}
