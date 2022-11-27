package com.example.jpa;

import com.example.jpa.entity.*;
import com.example.jpa.repository.BannersRepository;
import com.example.jpa.repository.CampaignsRepository;
import com.example.jpa.repository.ClientsRepository;
import com.example.jpa.repository.CreativesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class RelationShipTest {

    @Autowired
    BannersRepository bannersRepository;
    @Autowired
    CampaignsRepository campaignsRepository;
    @Autowired
    ClientsRepository clientsRepository;
    @Autowired
    CreativesRepository creativesRepository;


    // 1번 테스트
    @Test
    void 클라이언트의_캠페인을_가져온다_n_plus_1() {
        List<Clients> clients = clientsRepository.findAll();

        Assertions.assertThat(clients.size()).isEqualTo(2);

        for (Clients client : clients) {
            List<Campaigns> campaigns = client.getCampaigns();

            for (Campaigns campaign : campaigns) {
                System.out.println(campaign.getCampaignName());
            }
            System.out.println("==========================");
        }
    }

    // 2번 테스트
    @Test
    void 클라이언트의_캠페인을_가져온다_fetch_join() {
        List<Clients> clients = clientsRepository.getClientsWithCampaigns();

        System.out.println("@@ size : " + clients.size());
        Assertions.assertThat(clients.size()).isEqualTo(5);

        for (Clients client : clients) {
            List<Campaigns> campaigns = client.getCampaigns();

            for (Campaigns campaign : campaigns) {
                System.out.println(campaign.getCampaignName());
            }
            System.out.println("==========================");
        }
    }

    // 3번테스트
    @Test
    void 클라이언트의_캠페인을_가져온다_fetch_join_by_distinct() {
        List<Clients> clients = clientsRepository.getClientsWithCampaignsByDistinct();

        System.out.println("@@ size : " + clients.size());
        Assertions.assertThat(clients.size()).isEqualTo(2);

        for (Clients client : clients) {
            List<Campaigns> campaigns = client.getCampaigns();

            for (Campaigns campaign : campaigns) {
                System.out.println(campaign.getCampaignName());
            }
            System.out.println("==========================");
        }
    }

    @Test
    void 클라이언트의_extra_features_where_애노테이션() {
        Clients clients = clientsRepository.findById(1L).orElseThrow();

        Long idx = clients.getExtraFeatures().get(0).getIdx();

        System.out.println(idx);
    }

    @Test
    void 클라이언트의_extra_features_페치조인_where_애노테이션() {
        Clients clients = clientsRepository.getClientsWithExtraFeatures(1L);

        Long idx = clients.getExtraFeatures().get(0).getIdx();

        System.out.println(idx);
    }

    @Test
    void 캠페인의_클라이언트를_가져온다() {
        List<Campaigns> all = campaignsRepository.findAll();

        all.forEach(campaigns -> campaigns.getClients().getClientName());
    }

    @Test
    void 배너1에_연결되어있는_소재() {
        Banners banners = bannersRepository.findById(1L).orElseThrow();

        List<BannerCreatives> bannerCreatives = banners.getBannerCreatives();

        StringBuffer sb = new StringBuffer();
        for (BannerCreatives bannerCreative : bannerCreatives) {
            sb.append(bannerCreative.getCreatives().getCreativeName()).append("\n");
        }

        System.out.println(sb);
    }

    @Test
    void 소재1에_연결되어있는_배너() {
        Creatives creatives = creativesRepository.findById(1L).orElseThrow();

        List<BannerCreatives> bannerCreatives = creatives.getBannerCreatives();

        StringBuffer sb = new StringBuffer();
        for (BannerCreatives bannerCreative : bannerCreatives) {
            sb.append(bannerCreative.getBanners().getBannerName()).append("\n");
        }

        System.out.println(sb);
    }
}
