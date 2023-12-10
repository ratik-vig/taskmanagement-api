package com.rjd.taskmananger.services;

import com.rjd.taskmananger.dto.UserDto;
import com.rjd.taskmananger.dto.UserLoginRequest;
import com.rjd.taskmananger.dto.UserLoginResponse;
import com.rjd.taskmananger.exceptions.DuplicateUserException;
import com.rjd.taskmananger.model.User;
import com.rjd.taskmananger.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public UserDto registerUser(User user) throws DuplicateUserException {

        User newUser = null;
        try{
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            newUser = userRepository.save(user);
        }catch(DataIntegrityViolationException de){
            throw new DuplicateUserException("User already exists with email", de);
        }
        return new UserDto(
                newUser.getUserId(),
                newUser.getUserFname(),
                newUser.getUserLname(),
                newUser.getUserEmail(),
                null
        );
    }

    public UserLoginResponse loginUser(UserLoginRequest request){
        System.out.println(request.userEmail());
        System.out.println(request.userPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.userEmail(), request.userPassword()));
        User user = userRepository.findByUserEmail(request.userEmail()).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
        return new UserLoginResponse(jwtService.generateToken(user));
    }
}
