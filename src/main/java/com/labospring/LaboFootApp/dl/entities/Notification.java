package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Notification {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private boolean readStatus;

    @ManyToOne
    private Message message;
}
