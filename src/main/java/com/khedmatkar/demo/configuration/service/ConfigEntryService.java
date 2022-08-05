package com.khedmatkar.demo.configuration.service;

import com.khedmatkar.demo.configuration.dto.ConfigEntryDTO;
import com.khedmatkar.demo.configuration.entity.ConfigEntry;
import com.khedmatkar.demo.configuration.repository.ConfigEntryRepository;
import com.khedmatkar.demo.exception.ConfigEntryNotModifiable;
import com.khedmatkar.demo.service.domain.NewCertificatedSpecialistFinder;
import com.khedmatkar.demo.service.domain.RandomCertificatedSpecialistFinder;
import com.khedmatkar.demo.service.domain.RandomSpecialistFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ConfigEntryService {
    private final ConfigEntryRepository configEntryRepository;
    public static final String SPECIALIST_FINDER_STRATEGY_KEY = "SPECIALIST_FINDER_STRATEGY";
    public static final String CONCURRENT_ONGOING_SERVICES_LIMIT_KEY = "CONCURRENT_ONGOING_SERVICES_LIMIT";
    public static final List<String> SPECIALIST_FINDER_STRATEGY_CLASSES =
            List.of(
                    RandomSpecialistFinder.class.getCanonicalName(),
                    NewCertificatedSpecialistFinder.class.getCanonicalName(),
                    RandomCertificatedSpecialistFinder.class.getCanonicalName());

    public ConfigEntryService(ConfigEntryRepository configEntryRepository) {
        this.configEntryRepository = configEntryRepository;
    }

    public void updateConfigEntry(ConfigEntryDTO dto) {
        var configEntry = configEntryRepository.findByKey(dto.key)
                .orElse(
                        ConfigEntry
                                .builder()
                                .key(dto.key)
                                .build()
                );
        configEntry.setValue(dto.value);
        checkModifiability(configEntry);
        configEntryRepository.save(configEntry);
    }

    public void checkModifiability(ConfigEntry configEntry) {
        var key = configEntry.getKey();
        var value = configEntry.getValue();
        switch (key) {
            case CONCURRENT_ONGOING_SERVICES_LIMIT_KEY:
                try {
                    Integer.parseInt(value);
                } catch (NumberFormatException nfe) {
                    throw new ConfigEntryNotModifiable();
                }
                break;
            case SPECIALIST_FINDER_STRATEGY_KEY:
                if (!SPECIALIST_FINDER_STRATEGY_CLASSES.contains(value)) {
                    throw new ConfigEntryNotModifiable();
                }
                break;
            default:
        }
    }


    public Optional<ConfigEntry> getValue(String key) {
        return configEntryRepository.findByKey(key);
    }
}
