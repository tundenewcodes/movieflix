 

package com.movieflix.movieflix.auth.services;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movieflix.movieflix.auth.entitties.User;
import com.movieflix.movieflix.auth.entitties.UserRole;
import com.movieflix.movieflix.auth.repositories.UserRepository;
import com.movieflix.movieflix.auth.utils.AuthResponse;
import com.movieflix.movieflix.auth.utils.LoginRequest;
import com.movieflix.movieflix.auth.utils.RegisterRequest;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    // Constructor for dependency injection
    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, 
                       JwtService jwtService, RefreshTokenService refreshTokenService,
                       AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    // Register method
    public AuthResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
            .name(registerRequest.getName())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .username(registerRequest.getUsername())  // Fixing method call
            .role(UserRole.USER).build();            // Ensure 'build()' is called at the end

        User savedUser = userRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken.getRefreshToken())
            .build();
    }

    // Login method
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
            )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("No user with this email!"));

        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken.getRefreshToken())
            .build();
    }
}
