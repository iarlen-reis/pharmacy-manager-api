package com.remedy.iarlen.course.controllers;

import com.remedy.iarlen.course.Remedy.RemedyWithIdDTO;
import com.remedy.iarlen.course.Remedy.RemedyDTO;
import com.remedy.iarlen.course.models.RemedyModel;
import com.remedy.iarlen.course.repositories.RemedyRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/remedy")
public class RemedyController {

    @Autowired
    private RemedyRepository remedyRepository;

    @PostMapping
    private ResponseEntity<RemedyDTO> createRemedy(@RequestBody @Valid RemedyDTO data) {
        this.remedyRepository.save(new RemedyModel(data));
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @GetMapping
    public ResponseEntity<List<RemedyWithIdDTO>> allRemedy() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(remedyRepository.findAll()
                        .stream()
                        .map(RemedyWithIdDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RemedyWithIdDTO> getRemedyById(@PathVariable Long id) {
        Optional<RemedyWithIdDTO> remedy = this.remedyRepository.findById(id)
                .stream().map(RemedyWithIdDTO::new).findFirst();

        if (!remedy.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(remedy.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RemedyWithIdDTO> updateRemedyById(
            @PathVariable Long id, @RequestBody @Valid RemedyDTO remedy) {

        Optional<RemedyModel> findRemedy = this.remedyRepository.findById(id);

        if (!findRemedy.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        findRemedy.get().updateData(remedy);
        this.remedyRepository.save(findRemedy.get());

        return ResponseEntity.status(HttpStatus.OK).body(
                findRemedy.stream().map(RemedyWithIdDTO::new).findFirst().get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RemedyWithIdDTO> deleteRemedyById(@PathVariable Long id) {
        Optional<RemedyModel> findRemedy = this.remedyRepository.findById(id);

        if (!findRemedy.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        this.remedyRepository.delete(findRemedy.get());

        return ResponseEntity.status(HttpStatus.OK).body(
                findRemedy.stream().map(RemedyWithIdDTO::new).findFirst().get());
    }
}
