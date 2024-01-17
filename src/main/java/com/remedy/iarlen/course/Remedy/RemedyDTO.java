package com.remedy.iarlen.course.Remedy;

import java.time.LocalDate;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RemedyDTO(
  @NotBlank
  String name,
  
  @Enumerated
  Via via,
  
  @NotBlank
  String batch,
  
  @NotNull
  int amount, 

  @Future
  LocalDate validity, 

  @Enumerated
  Laboratory laboratory
  ) {
}
