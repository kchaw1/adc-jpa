package com.example.jpa.repository;

import com.example.jpa.entity.Geo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoRepository extends JpaRepository<Geo, Long> {
}
