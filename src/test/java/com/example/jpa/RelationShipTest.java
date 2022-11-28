package com.example.jpa;

import com.example.jpa.entity.*;
import com.example.jpa.repository.BannersRepository;
import com.example.jpa.repository.CampaignsRepository;
import com.example.jpa.repository.ClientsRepository;
import com.example.jpa.repository.CreativesRepository;
import com.example.jpa.repository.FileInfoRepository;
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
    @Autowired
    FileInfoRepository fileInfoRepository;


    // 1번 테스트
    @Test
    void 클라이언트의_캠페인을_가져온다_n_plus_1() {
        /**
         * 클라이언트 조회쿼리 1번
         * 각각의 클라이언트 id로 campaign을 조회하는 쿼리 2번 총 3번 쿼리 실행
         *
         * default_batch_fetch_size 를 설정시 n 번 쿼리가 in 절로 1번에 실행된다.
         */
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
        /**
         * fetch join을 통해 한번에 가져옴
         * 하지만 DB에서 뻥튀기 되는 개념이 client에 그대로 적용되어
         * 클라이언트의 사이즈가 5가 된다.
         */
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
        /**
         * fetch join을 통해 한번에 가져옴
         * Clients 의 뻥튀기된 데이터를 distinct를 통해 제거한다.
         */
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
    void 클라이언트의_file_info() {
        /**
         * 클라이언트를 통해 fileInfo를 가져온다
         * Lazy 로딩으로 쿼리 2회 실행
         */
        Clients clients = clientsRepository.findById(1L).orElseThrow();

        String fileName = clients.getFileInfo().getFileName();

        System.out.println(fileName);
    }

    @Test
    void 클라이언트의_file_info_fetch_join() {
        /**
         * 클라이언트를 통해 fileInfo를 가져온다
         * fetch join으로 한번에 가져옴
         */
        Clients clients = clientsRepository.getClientsWithFileInfo(1L);

        String fileName = clients.getFileInfo().getFileName();

        System.out.println(fileName);
    }

    @Test
    void file_info_조회() {
        /**
         * onetoone 양방향에서 FK가 없으므로 LAZY로딩이 적용되지 않는다.
         * client를 조회하지 않았음에도 바로 가져옴.
         * file info 에서 client의 연관관계를 끊으면 file info만 조회한다.
         */

        FileInfo fileInfo = fileInfoRepository.findById(1L).orElseThrow();

        System.out.println(fileInfo);
    }

    @Test
    void 클라이언트의_extra_features_where_애노테이션() {
        /**
         * 1. @Where(clause = "context='clients'") 가 잘 동작하는지 확인
         * 2. 쿼리가 몇번 실행되는지 확인
         */
        Clients clients = clientsRepository.findById(1L).orElseThrow();

        Long idx = clients.getExtraFeatures().get(0).getIdx();

        System.out.println(idx);
    }

    @Test
    void 클라이언트의_extra_features_페치조인_where_애노테이션() {
        /**
         * 1. @Where(clause = "context='clients'") 가 조인조건으로 잘 들어가는지 확인
         * 2. 쿼리가 몇번 실행되는지 확인
         */
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
        /**
         * 배너 조회 후
         * 배너의 BannerCreatives 를 통해 소재를 조회한다.
         */
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
        /**
         * 소재 조회 후
         * 소재의 BannerCreatives 를 통해 배너를 조회한다.
         */
        Creatives creatives = creativesRepository.findById(1L).orElseThrow();

        List<BannerCreatives> bannerCreatives = creatives.getBannerCreatives();

        StringBuffer sb = new StringBuffer();
        for (BannerCreatives bannerCreative : bannerCreatives) {
            sb.append(bannerCreative.getBanners().getBannerName()).append("\n");
        }

        System.out.println(sb);
    }
}
