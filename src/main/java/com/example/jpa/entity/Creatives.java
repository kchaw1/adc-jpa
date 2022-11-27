package com.example.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ox_creatives")
public class Creatives {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "creative_id")
    private Long creativeId;

    @Column(name = "creative_name")
    private String creativeName;

    @OneToMany(mappedBy = "creatives")
    private List<BannerCreatives> bannerCreatives = new ArrayList<>();
}
