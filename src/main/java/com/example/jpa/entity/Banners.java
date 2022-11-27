package com.example.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ox_banners")
public class Banners {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "banner_id")
    private Long bannerId;

    @Column(name = "banner_name")
    private String bannerName;

    @OneToMany(mappedBy = "banners")
    private List<BannerCreatives> bannerCreatives = new ArrayList<>();
}
