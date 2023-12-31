package com.rjd.taskmananger.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_fname", nullable = false)
    private String userFname;

    @Column(name="user_lname", nullable = false)
    private String userLname;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name="user_pwd", nullable = false)
    private String userPassword;

    @ManyToMany
    @JoinTable(
            name="user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="project_id")
    )
    private Set<Project> projects;

    public User(String userFname, String userLname, String userEmail, String userPwd) {
        this.userFname = userFname;
        this.userLname = userLname;
        this.userEmail = userEmail;
        this.userPassword = userPwd;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
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
