package com.rjd.taskmananger.services;

import com.rjd.taskmananger.dto.UserDto;
import com.rjd.taskmananger.dto.UserRequest;
import com.rjd.taskmananger.exceptions.DuplicateUserException;
import com.rjd.taskmananger.model.User;
import com.rjd.taskmananger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public UserDto registerUser(User user) throws DuplicateUserException {

        User newUser = null;
        try{
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
}
