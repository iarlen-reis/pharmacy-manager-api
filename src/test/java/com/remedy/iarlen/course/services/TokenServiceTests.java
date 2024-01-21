package com.remedy.iarlen.course.services;

import com.remedy.iarlen.course.User.UserRole;
import com.remedy.iarlen.course.models.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TokenServiceTests {

    @Mock
    private TokenService tokenService;

    private UserModel user;
    @BeforeEach
    public void setUp() {
        this.user = new UserModel(1L, "Iarlenzito", "teste", UserRole.USER);
        ReflectionTestUtils.setField(tokenService, "secret", "1234");


    }

    @Test
    @DisplayName("Should generate a token for user")
    public void generateToken() {
        when(tokenService.generateToken(any(UserModel.class))).thenCallRealMethod();

        String result = tokenService.generateToken(this.user);

        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Should verify a token of user")
    public void verifyToken() {
        when(tokenService.generateToken(any(UserModel.class))).thenCallRealMethod();
        when(tokenService.verifyToken(any())).thenCallRealMethod();

        String token = tokenService.generateToken(this.user);

        String result = this.tokenService.verifyToken(token);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, "Iarlenzito");
    }

}
