package com.remedy.iarlen.course.services;

import com.remedy.iarlen.course.User.UserRole;
import com.remedy.iarlen.course.models.UserModel;
import com.remedy.iarlen.course.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    private UserModel user;

    @BeforeEach
    public void setUp() {
        this.user = new UserModel(1L, "Iarlenzito", "teste1", UserRole.USER);
    }

    @Test
    @DisplayName("Should find user by username")
    public void loadUserByUsername() {
        when(this.userRepository.findByUsername(eq("Iarlenzito"))).thenReturn(this.user);

        UserDetails result = this.authService.loadUserByUsername("Iarlenzito");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), this.user.getUsername());
    }
}
