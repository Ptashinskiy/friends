package com.skysoft.friends.security.build_token;

import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.bussines.exception.UserException;
import com.skysoft.friends.model.UserEntity;
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
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> NotFoundException.userNotFound(email));
        if (user.isConfirmed()) {
            return new CustomUserDetails(user.getEmail(), user.getCredentials().getPassword());
        } else throw UserException.confirmationRequired(user.getEmail());
    }
}
