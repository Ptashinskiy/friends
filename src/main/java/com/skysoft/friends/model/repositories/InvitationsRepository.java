package com.skysoft.friends.model.repositories;

import com.skysoft.friends.model.entities.InvitationEntity;
import com.skysoft.friends.model.entities.InvitationStatus;
import com.skysoft.friends.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvitationsRepository extends JpaRepository<InvitationEntity, UUID> {

    boolean existsByInvitationTargetAndInvitationSenderAndStatus(UserEntity invitationTarget, UserEntity invitationSender, InvitationStatus status);

    Optional<InvitationEntity> findByInvitationSenderAndInvitationTargetAndStatus(UserEntity invitationSender, UserEntity invitationTarget, InvitationStatus status);
}
