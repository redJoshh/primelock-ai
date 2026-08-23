package com.primelock.coreengine.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted=false")
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false, unique = true)
    private String email;

    // We must ensure the user has a password field for authentication
    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "bpt_start_time", nullable = false)
    private LocalTime bptStartTime;

    @Column(name = "bpt_end_time", nullable = false)
    private LocalTime bptEndTime;

    // --- SPRING SECURITY USERDETAILS METHODS --- //

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // By default, we give every registered user a standard "USER" role
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        // Spring Security uses "username" under the hood, but we use email for logins.
        return this.email;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
