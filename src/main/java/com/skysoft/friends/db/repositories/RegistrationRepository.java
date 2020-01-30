package com.skysoft.friends.db.repositories;

import com.skysoft.friends.model.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
interface RegistrationRepository extends JpaRepository<RegistrationEntity, UUID> {

    boolean existsByEmailOrUserNameAndConfirmed(String email, String userName, boolean confirmed);

    Optional<RegistrationEntity> findByVerificationTokenAndConfirmedAndExpireDateBefore(String verificationToken, boolean confirmed, Instant expireDate);
}
