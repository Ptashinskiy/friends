package com.skysoft.friends.model.repositories;

import com.skysoft.friends.model.entities.InvitationEntity;
import com.skysoft.friends.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvitationsRepository extends JpaRepository<InvitationEntity, UUID> {

    boolean existsByInvitationTargetAndInvitationSender(UserEntity invitationTarget, UserEntity invitationSender);

    Optional<InvitationEntity> findByInvitationSenderAndInvitationTarget(UserEntity invitationSender, UserEntity invitationTarget);
}
