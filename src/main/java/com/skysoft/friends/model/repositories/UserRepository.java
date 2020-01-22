package com.skysoft.friends.model.repositories;

import com.skysoft.friends.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    @Query(nativeQuery = true, value = "select ue.* from user_entity as ue where ue.email = :loginParameter or ue.user_name = :loginParameter")
    Optional<UserEntity> findByEmailOrUserName(@Param("loginParameter") String login);

    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "select ue.confirmation_code from user_entity as ue where ue.email = :email")
    Optional<Integer> getConfirmationCodeByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "select ue.user_name from user_entity as ue where ue.user_name = :loginParameter or ue.email = :loginParameter")
    Optional<String> getUserNameByLoginParameter(@Param("loginParameter") String loginParameter);
}
