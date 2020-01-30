package com.skysoft.friends.security.build_token;

import com.skysoft.friends.db.repositories.UsersDbService;
import com.skysoft.friends.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersDbService usersDbService;

    @Override
    public UserDetails loadUserByUsername(String loginParameter) {
        UserEntity user = usersDbService.getUserByUserName(loginParameter);
        return new CustomUserDetails(user.getUserName(), user.getPassword());
    }
}
