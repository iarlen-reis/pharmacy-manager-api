package com.remedy.iarlen.course.controllers;

import com.remedy.iarlen.course.Auth.AuthDTO;
import com.remedy.iarlen.course.Auth.AuthResponseDTO;
import com.remedy.iarlen.course.models.UserModel;
import com.remedy.iarlen.course.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AuthDTO user) {
        var token = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        var authentication = this.manager.authenticate(token);

        var tokenJWT = tokenService.generateToken((UserModel) authentication.getPrincipal());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponseDTO(tokenJWT));
    }
}
