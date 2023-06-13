package com.conexia.api.notification;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long authorId;

    @Column
    private Long targetId;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private Boolean readed;
}
