package com.example.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ox_campaigns")
public class Campaigns {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "campaign_id")
    private Long campaignId;

    @Column(name = "campaign_name")
    private String campaignName;

    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne 양방향
    @JoinColumn(name = "client_id")
    private Clients clients;
}
