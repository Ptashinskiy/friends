package com.skysoft.friends.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@MappedSuperclass
@EqualsAndHashCode
class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, updatable = false)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @CreationTimestamp
    private Instant createdTime;

    @UpdateTimestamp
    private Instant lastUpdatedTime;

}
