package com.skysoft.friends.security.build_token;

import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.bussines.exception.UserException;
import com.skysoft.friends.model.entities.UserEntity;
import com.skysoft.friends.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginParameter) {
        UserEntity user = userRepository.findByEmailOrUserName(loginParameter).orElseThrow(() -> NotFoundException.userNotFound(loginParameter));
        if (user.isEmailConfirmed()) {
            return new CustomUserDetails(loginParameter, user.getPassword());
        } else throw UserException.confirmationRequired(user.getEmail());
    }
}
