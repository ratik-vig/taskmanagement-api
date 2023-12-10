package com.rjd.taskmananger.services;

import com.rjd.taskmananger.dto.UserDto;
import com.rjd.taskmananger.exceptions.DuplicateUserException;
import com.rjd.taskmananger.model.User;
import com.rjd.taskmananger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
                UserDetails user =  userRepository.findByUserEmail(userEmail)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found with ID: " + userEmail)
                        );
                System.out.println(user.getPassword());
                return user;
            }
        };
    }


}
