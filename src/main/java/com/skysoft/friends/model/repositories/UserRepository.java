package com.skysoft.friends.model.repositories;

import com.skysoft.friends.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "select uc.confirmation_code from user_entity as ue" +
            " inner join user_credentials as uc on ue.id = uc.user_entity_id where ue.email = :email and uc.confirmed = false")
    Optional<Integer> getConfirmationCodeByEmail(@Param("email") String email);
}
