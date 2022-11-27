package com.example.jpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ox_banner_creatives")
public class BannerCreatives {

    @EmbeddedId
    private BannerCreativesId bannerCreativesId;

    @MapsId("bannerId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id", insertable = false, updatable = false)
    private Banners banners;

    @MapsId("creativeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creative_id", insertable = false, updatable = false)
    private Creatives creatives;

    @Embeddable
    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class BannerCreativesId implements Serializable {

        private static final long serialVersionUID = -2521155422782532L;

        @Column(name = "banner_id")
        private Long bannerId;

        @Column(name = "creative_id")
        private Long creativeId;
    }
}
