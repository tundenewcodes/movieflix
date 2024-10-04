package com.movieflix.movieflix.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieflix.movieflix.auth.entitties.RefreshToken;
import com.movieflix.movieflix.auth.entitties.User;
import com.movieflix.movieflix.auth.services.AuthService;
import com.movieflix.movieflix.auth.services.JwtService;
import com.movieflix.movieflix.auth.services.RefreshTokenService;
import com.movieflix.movieflix.auth.utils.AuthResponse;
import com.movieflix.movieflix.auth.utils.LoginRequest;
import com.movieflix.movieflix.auth.utils.RefreshTokenRequest;
import com.movieflix.movieflix.auth.utils.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService) {
        this.authService = authService;
        this.refreshTokenService = null;
        this.jwtService = new JwtService();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());

        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);
        return ResponseEntity.ok(
                AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken.getRefreshToken()).build());

    }

}
