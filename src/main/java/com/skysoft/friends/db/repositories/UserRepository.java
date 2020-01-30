package com.skysoft.friends.db.repositories;

import com.skysoft.friends.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUserName(String userName);

    boolean existsByEmailOrUserName(String email, String userName);

    @Query(nativeQuery = true, value = "select * from user_entity as ue where ue.id not in " +
            "(select fe.friend_id from friend_entity as fe where fe.friend_owner_id = " +
            "(select ue.id from user_entity as ue where ue.user_name = :userName)) " +
            "and ue.id not in " +
            "(select ie.invitation_target_id from invitation_entity as ie where ie.invitation_sender_id = " +
            "(select ue.id from user_entity as ue where ue.user_name = :userName) " +
            "          or ie.invitation_target_id = (select ue.id from user_entity as ue where ue.user_name = :userName)) " +
            "and ue.id != (select ue.id from user_entity as ue where ue.user_name = :userName)")
    List<UserEntity> getAllNonFriendUsers(@Param("userName") String userName);
}
