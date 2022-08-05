package com.khedmatkar.demo.service.domain;

import com.khedmatkar.demo.configuration.service.ConfigEntryService;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service
public class SpecialistFinderFactory {
    private final ConfigEntryService configEntryService;
    private final ApplicationContext context;

    public SpecialistFinderFactory(ConfigEntryService configEntryService,
                                   ApplicationContext context) {
        this.configEntryService = configEntryService;
        this.context = context;
    }

    @SneakyThrows
    public SpecialistFinderStrategy getSpecialistFinder() {
        var className = configEntryService.getValue(ConfigEntryService.SPECIALIST_FINDER_STRATEGY_KEY)
                .orElseThrow()
                .getValue();
        Class<?> aClass = Class.forName(className);
        return (SpecialistFinderStrategy) context.getBean(aClass);
    }
}
