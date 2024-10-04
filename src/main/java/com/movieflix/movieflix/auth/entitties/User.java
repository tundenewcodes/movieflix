package com.movieflix.movieflix.auth.entitties;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank(message = "please ensure this field is not empty")
    private String name;
    
    @NotBlank(message = "please ensure this field is not empty")
    @Email(message = "ensure you enter email properly")
    private String email;
    
    @NotBlank(message = "please ensure this field is not empty")
    private String username;
    
    @NotBlank(message = "please ensure this field is not empty")
    @Column(unique = true)
    @Size(min = 6, message = "password is required and should be between 8 - 20 characters")
    private String password;


    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isEnabled = true;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return username;
    }
    
    @Override
    public boolean isEnabled(){
        return isEnabled;
    }
    
    @Override
    public boolean isAccountNonExpired(){
        return isAccountNonExpired;
    }

    @Override 
    public boolean isAccountNonLocked(){
        return isAccountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired(){
        return isCredentialsNonExpired;
    }
}
