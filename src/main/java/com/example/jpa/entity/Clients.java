package com.example.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ox_clients")
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "client_name")
    private String clientName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileInfo fileInfo;

    @OneToOne(fetch = FetchType.LAZY) // OneToOne 단방향
    @JoinColumn(name = "account_id")
    private Accounts accounts;

    @OneToMany(mappedBy = "clients") // OneToMay 양방향
    private List<Campaigns> campaigns = new ArrayList<>();

    @OneToMany(mappedBy = "clients") // OneToOne 인데 OneToMany로 풀은 형향
    @Where(clause = "context='clients'")
    private List<ExtraFeature> extraFeatures = new ArrayList<>();
}
