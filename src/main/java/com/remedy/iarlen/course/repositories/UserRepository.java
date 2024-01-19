package com.remedy.iarlen.course.repositories;

import com.remedy.iarlen.course.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserDetails findByUsername(String username);
}
