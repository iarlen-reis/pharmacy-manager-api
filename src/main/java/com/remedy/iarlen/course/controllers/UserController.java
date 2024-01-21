package com.remedy.iarlen.course.controllers;

import com.remedy.iarlen.course.User.CreateUserDTO;
import com.remedy.iarlen.course.User.GetUserDTO;
import com.remedy.iarlen.course.User.ResponseUserDTO;
import com.remedy.iarlen.course.User.UpdateUserDTO;
import com.remedy.iarlen.course.repositories.UserRepository;
import com.remedy.iarlen.course.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Autowired
    UserService userService;

    @Operation(summary = "This method is used to create a user.")
    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody @Valid CreateUserDTO data) {
        if(userRepository.findByUsername(data.username()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseUserDTO("Usuário já existente."));
        }

        return ResponseEntity.ok().body(userService.createUser(data));
    }

    @Operation(summary = "This method is used to get current user.")
    @GetMapping
    public ResponseEntity<GetUserDTO> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getCurrentUser(userDetails.getUsername(), userDetails.getAuthorities()));
    };

    @Operation(summary = "This method is used to update user password.")
    @PutMapping
    public ResponseEntity<ResponseUserDTO> updateUser(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpdateUserDTO data) {

        if(!passwordEncoder.matches(data.oldPassword(), userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseUserDTO("A senha antiga é inválida."));
        }

        return ResponseEntity.ok()
                .body(userService.updateUser(userDetails.getUsername(), data.newPassword()));
    };

    @Operation(summary = "This method is used to current user delete your account.")
    @DeleteMapping
    public ResponseEntity<ResponseUserDTO> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(userService.deleteUser(userDetails.getUsername()));
    }
}
