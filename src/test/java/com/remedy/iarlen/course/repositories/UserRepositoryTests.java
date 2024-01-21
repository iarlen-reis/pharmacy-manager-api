package com.remedy.iarlen.course.repositories;

import com.remedy.iarlen.course.User.UserRole;
import com.remedy.iarlen.course.models.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private UserModel user;


    @BeforeEach
    public void setUserConfiguration() {
        this.user = new UserModel("iarlenzito", "codefalls1", UserRole.ADMIN);


    }

    @Test
    public void shouldSaveUser() {
        UserModel savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser, user);
        Assertions.assertNotNull(savedUser.getId());
    }

    @Test
    public void shouldGetAllUsersAndCompareTheSize() {
        this.userRepository.save(this.user);

        List<UserModel> users = userRepository.findAll();

        Assertions.assertEquals(users.size(), 1);
    }

    @Test
    public void shouldGetUserByUsername() {
        this.userRepository.save(this.user);

        UserModel userFound = (UserModel) userRepository.findByUsername(this.user.getUsername());

        Assertions.assertEquals(userFound.getId(), this.user.getId());
        Assertions.assertEquals(userFound.getUsername(), this.user.getUsername());
    }

    @Test
    public void shouldDeleteAUser() {
        this.userRepository.save(this.user);

        UserModel userFound = (UserModel) userRepository.findByUsername(this.user.getUsername());
        userRepository.delete(userFound);

        List<UserModel> allUsers = this.userRepository.findAll();

        Assertions.assertEquals(allUsers.size(), 0);
    }
}
