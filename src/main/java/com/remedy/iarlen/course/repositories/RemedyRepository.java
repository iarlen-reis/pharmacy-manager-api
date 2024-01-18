package com.remedy.iarlen.course.repositories;

import com.remedy.iarlen.course.models.RemedyModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RemedyRepository extends JpaRepository<RemedyModel, Long> {

  List<RemedyModel> findAllByActiveTrue();

}
