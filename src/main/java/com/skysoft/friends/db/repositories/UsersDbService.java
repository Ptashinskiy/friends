package com.skysoft.friends.db.repositories;

import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.bussines.exception.RegistrationException;
import com.skysoft.friends.model.entities.FriendStatus;
import com.skysoft.friends.model.entities.RegistrationEntity;
import com.skysoft.friends.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersDbService {

    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    private final FriendsRepository friendsRepository;

    public boolean suchUserNotExist(String email, String userName) {
        return !userRepository.existsByEmailOrUserName(email, userName);
    }

    public void saveRegistration(RegistrationEntity registrationEntity) {
        registrationRepository.save(registrationEntity);
    }

    public RegistrationEntity getRegisteredUserByVerificationToken(String verificationToken) {
        return registrationRepository.findByVerificationTokenAndConfirmedAndExpireDateBefore(verificationToken, false, Instant.now())
                .orElseThrow(RegistrationException::invalidVerificationToken);
    }

    public boolean suchUserNotConfirmed(String email, String userName) {
        return !registrationRepository.existsByEmailOrUserNameAndConfirmed(email, userName, true);
    }

    public void saveNewUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> NotFoundException.userNotFound(userName));
    }

    public List<UserEntity> getAllNonFriendUsers(String userName) {
        return userRepository.getAllNonFriendUsers(userName);
    }

    public boolean alreadyFriends(UserEntity friend1, UserEntity friend2) {
        return friendsRepository.existsByFriendOwnerAndFriendAndStatus(friend1, friend2, FriendStatus.FRIENDS);
    }
}
