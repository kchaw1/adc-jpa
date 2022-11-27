package com.example.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ox_extra_feature")
public class ExtraFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idx")
    private Long idx;

    @Column(name = "context_id")
    private Long contextId;

    @Column(name = "context")
    private String context;

    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne 양방향
    @JoinColumn(name = "context_id", insertable = false, updatable = false)
    private Clients clients;
}
