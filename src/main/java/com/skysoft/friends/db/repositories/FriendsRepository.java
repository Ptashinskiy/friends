package com.skysoft.friends.db.repositories;

import com.skysoft.friends.model.entities.FriendEntity;
import com.skysoft.friends.model.entities.FriendStatus;
import com.skysoft.friends.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FriendsRepository extends JpaRepository<FriendEntity, UUID> {

    boolean existsByFriendOwnerAndFriendAndStatus(UserEntity friend1, UserEntity friend2, FriendStatus status);
}
