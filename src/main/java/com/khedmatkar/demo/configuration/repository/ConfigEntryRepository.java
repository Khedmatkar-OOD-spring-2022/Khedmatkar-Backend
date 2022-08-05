package com.khedmatkar.demo.configuration.repository;

import com.khedmatkar.demo.configuration.entity.ConfigEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigEntryRepository extends JpaRepository<ConfigEntry, Long> {

    Optional<ConfigEntry> findByKey(String key);
}
