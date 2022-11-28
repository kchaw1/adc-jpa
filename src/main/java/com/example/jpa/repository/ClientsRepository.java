package com.example.jpa.repository;

import com.example.jpa.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientsRepository extends JpaRepository<Clients, Long> {

    @Query("select c " +
            "from Clients c " +
            "left join fetch c.campaigns")
    List<Clients> getClientsWithCampaigns();

    @Query("select distinct c " +
            "from Clients c " +
            "left join fetch c.campaigns")
    List<Clients> getClientsWithCampaignsByDistinct();

    @Query("select distinct c " +
            "from Clients c " +
            "left join fetch c.extraFeatures " + "where c.clientId = :clientId")
    Clients getClientsWithExtraFeatures(Long clientId);

    @Query("select distinct c " +
            "from Clients c " +
            "left join fetch c.fileInfo " + "where c.clientId = :clientId")
    Clients getClientsWithFileInfo(Long clientId);
}
