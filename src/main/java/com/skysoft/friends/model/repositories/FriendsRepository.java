package com.skysoft.friends.model.repositories;

import com.skysoft.friends.model.entities.FriendEntity;
import com.skysoft.friends.model.entities.FriendStatus;
import com.skysoft.friends.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendsRepository extends JpaRepository<FriendEntity, UUID> {

    boolean existsByFriendOwnerAndFriendAndStatus(UserEntity friendOwner, UserEntity friend, FriendStatus status);

}
