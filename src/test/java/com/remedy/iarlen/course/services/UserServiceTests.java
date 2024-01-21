package com.remedy.iarlen.course.services;

import com.remedy.iarlen.course.User.CreateUserDTO;
import com.remedy.iarlen.course.User.GetUserDTO;
import com.remedy.iarlen.course.User.ResponseUserDTO;
import com.remedy.iarlen.course.User.UserRole;
import com.remedy.iarlen.course.models.UserModel;
import com.remedy.iarlen.course.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GrantedAuthority grantedAuthority;

    @Mock
    private PasswordEncoder passwordEncoder;

    private CreateUserDTO createUserDTO;
    private UserModel user;

    @BeforeEach
    public void setUp() {
        this.user = new UserModel(1L, "Iarlenzito", "teste", UserRole.USER);

        this.createUserDTO = new CreateUserDTO("Iarlenzito", "teste", UserRole.USER);
    }

    @Test
    @DisplayName("Should create a user")
    public void saveTheUser() {
        when(this.userRepository.save(ArgumentMatchers.any(UserModel.class))).thenReturn(user);

        ResponseUserDTO userSaved = userService.createUser(createUserDTO);

        Assertions.assertEquals(userSaved.message(), "Usuário criado com sucesso!");
    }

    @Test
    @DisplayName("Should get a user")
    public void getCurrentUser() {
        String username = "usuarioteste";

        List<GrantedAuthority> roles = List.of(grantedAuthority);

        when(grantedAuthority.toString()).thenReturn("ROLE_USER");

        GetUserDTO result = userService.getCurrentUser(username, roles);

        Assertions.assertEquals("ROLE_USER", result.roles().get(0));
        Assertions.assertEquals("usuarioteste", result.username());
    }

    @Test
    @DisplayName("Should update user password")
    public void updateUser() {
        when(userRepository.findByUsername(eq("Iarlenzito"))).thenReturn(user);

        ResponseUserDTO result = userService.updateUser("Iarlenzito", "atualizada");

        Assertions.assertEquals("Senha atualizada com sucesso!", result.message());
    }

    @Test
    @DisplayName("Should delete a user")
    public void deleteUser() {
        when(userRepository.findByUsername(eq("Iarlenzito"))).thenReturn(user);

        ResponseUserDTO result = userService.deleteUser("Iarlenzito");

        verify(userRepository).delete(user);

        Assertions.assertEquals("Usuário excluido com sucesso!", result.message());
    }
}
