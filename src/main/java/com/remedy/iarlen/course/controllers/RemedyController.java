package com.remedy.iarlen.course.controllers;

import com.remedy.iarlen.course.Remedy.RemedyWithIdDTO;
import com.remedy.iarlen.course.Remedy.RemedyDTO;
import com.remedy.iarlen.course.models.RemedyModel;
import com.remedy.iarlen.course.repositories.RemedyRepository;
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
    private RemedyRepository remedyRepository;

    @Operation(summary = "This method is used to create a remedy.")
    @PostMapping
    public ResponseEntity<RemedyDTO> createRemedy(@RequestBody @Valid RemedyDTO data) {
        RemedyModel newRemedy = remedyRepository.save(new RemedyModel(data));

        return ResponseEntity.status(HttpStatus.CREATED).body(new RemedyDTO(newRemedy));
    }

    @Operation(summary = "This method is used to get the remedy.")
    @Transactional
    @GetMapping
    public ResponseEntity<List<RemedyWithIdDTO>> allRemedy() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(remedyRepository.findAllByActiveTrue()
                        .stream()
                        .map(RemedyWithIdDTO::new).toList());
    }

    @Operation(summary = "This method is used to get a remedy by id.")
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<RemedyWithIdDTO> getRemedyById(@PathVariable Long id) {
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new RemedyWithIdDTO(remedy));
    }

    @Operation(summary = "This method is used to update a remedy by id.")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<RemedyWithIdDTO> updateRemedyById(
            @PathVariable Long id, @RequestBody @Valid RemedyDTO data) {
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);
        remedy.updateData(data);

        return ResponseEntity.status(HttpStatus.OK).body(new RemedyWithIdDTO(remedy));
    }

    @Operation(summary = "This method is used to delete a remedy by id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemedyById(@PathVariable Long id) {
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);
        this.remedyRepository.delete(remedy);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "This method is used to disable a remedy by id.")
    @Transactional
    @DeleteMapping("disable/{id}")
    public ResponseEntity<Void> disableRemedyById(@PathVariable Long id) {
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);
        remedy.deactivate();

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "This method is used to enable a remedy by id.")
    @Transactional
    @DeleteMapping("enable/{id}")
    public ResponseEntity<Void> enableRemedyById(@PathVariable Long id) {
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);
        remedy.reactivate();

        return ResponseEntity.noContent().build();
    }
}
