package com.khedmatkar.demo.configuration.entity;

import com.khedmatkar.demo.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "config_entries")
public class ConfigEntry extends AbstractEntity {
    private String key;

    @Setter
    private String value;
}
