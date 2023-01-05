package com.example.jpa.controller;

import com.example.jpa.entity.Geo;
import com.example.jpa.repository.GeoRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("geo")
@RequiredArgsConstructor
public class GeoController {

    private final GeoRepository geoRepository;

    @GetMapping("/list")
    @Cacheable(value = "geo")
    public List<Geo> list() {
        List<Geo> list = geoRepository.findAll();
        return list;
    }

    @GetMapping("/one/{idx}")
    @Cacheable(value = "geo", key = "#p0")
    public Geo one(@PathVariable("idx") long id) {
        Geo geo = geoRepository.findById(id).orElseThrow();
        return geo;
    }

    @PostMapping
    @CacheEvict(value = "geo", allEntries = true)
    public Geo add(@RequestBody GeoSaveRequest request) {
        Geo addGeo = Geo.builder()
                .geoCode(request.geoCode)
                .geoName(request.geoName)
                .build();

        return geoRepository.save(addGeo);
    }

    record GeoSaveRequest(
            @JsonProperty("geo_code") String geoCode,
            @JsonProperty("geo_name") String geoName
    ) {

    }
}
