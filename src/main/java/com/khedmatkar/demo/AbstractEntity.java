package com.khedmatkar.demo;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(unique = true)
    private UUID uuid = UUID.randomUUID();

    @Builder.Default
    @CreationTimestamp
    private LocalDateTime creation = LocalDateTime.now();

    @Builder.Default
    @UpdateTimestamp
    private LocalDateTime lastUpdate = LocalDateTime.now();

    @Version
    private Integer version;
}
