package com.remedy.iarlen.course.Remedy;

import java.time.LocalDate;

import com.remedy.iarlen.course.models.RemedyModel;

public record RemedyWithIdDTO(
    Long id,
    String name,
    Via via,
    LocalDate validity,
    int amount,
    String batch,
    Laboratory laboratory,
    boolean active
    ) {

  public RemedyWithIdDTO(RemedyModel remedy) {
    this(
        remedy.getId(),
        remedy.getName(),
        remedy.getVia(),
        remedy.getValidity(),
        remedy.getAmount(),
        remedy.getBatch(),
        remedy.getLaboratory(),
        remedy.isActive()        
        );
  }
}
