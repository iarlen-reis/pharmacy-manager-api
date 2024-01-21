package com.remedy.iarlen.course.services;

import com.remedy.iarlen.course.User.CreateUserDTO;
import com.remedy.iarlen.course.User.GetUserDTO;
import com.remedy.iarlen.course.User.ResponseUserDTO;
import com.remedy.iarlen.course.models.UserModel;
import com.remedy.iarlen.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseUserDTO createUser(CreateUserDTO data) {
        UserModel user = new UserModel(data.username(), data.password(), data.role());

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return new ResponseUserDTO("Usuário criado com sucesso!");
    }

    public GetUserDTO getCurrentUser(String username, Collection<? extends GrantedAuthority> roles) {
        return new GetUserDTO(username, roles.stream().map(Object::toString).toList());
    }

    public ResponseUserDTO updateUser(String username, String newPassword) {

        UserModel user = (UserModel) this.userRepository.findByUsername(username);

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return new ResponseUserDTO("Senha atualizada com sucesso!");
    };

    public ResponseUserDTO deleteUser(String username) {
        UserModel user = (UserModel) this.userRepository.findByUsername(username);

        userRepository.delete(user);

        return new ResponseUserDTO("Usuário excluido com sucesso!");
    }
}
