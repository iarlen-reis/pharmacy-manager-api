package com.remedy.iarlen.course.controllers;

import com.remedy.iarlen.course.User.CreateUserDTO;
import com.remedy.iarlen.course.User.GetUserDTO;
import com.remedy.iarlen.course.User.ResponseUserDTO;
import com.remedy.iarlen.course.User.UpdateUserDTO;
import com.remedy.iarlen.course.models.UserModel;
import com.remedy.iarlen.course.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody @Valid CreateUserDTO data) {

        if(userRepository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest()
                    .body(new ResponseUserDTO("Usuário já existente."));
        }

        UserModel user = new UserModel(data);

        String encodedPassword = passwordEncoder.encode(data.password());
        user.setPassword(encodedPassword);

        UserModel userCreated = this.userRepository.save(user);

        System.out.println(userCreated);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseUserDTO("Usuário criado com sucesso!"));
    }

    @GetMapping
    public ResponseEntity<GetUserDTO> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(new GetUserDTO(userDetails.getUsername()));
    };

    @PutMapping
    public ResponseEntity<ResponseUserDTO> updateUser(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpdateUserDTO data) {

        boolean isPassword = passwordEncoder.matches(data.oldPassword(), userDetails.getPassword());

        if(!isPassword) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseUserDTO("A senha antiga é inválida."));
        }

        UserModel user = (UserModel) this.userRepository.findByUsername(userDetails.getUsername());

        String encodedPassword = passwordEncoder.encode(data.newPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return ResponseEntity.ok().body(new ResponseUserDTO("Senha atualizada com sucesso!"));
    };

    @DeleteMapping
    public ResponseEntity<ResponseUserDTO> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserModel user = (UserModel) this.userRepository.findByUsername(userDetails.getUsername());
        userRepository.deleteById(user.getId());

        return ResponseEntity.ok().body(new ResponseUserDTO("Usuário deletado com sucesso!"));
    }
}
