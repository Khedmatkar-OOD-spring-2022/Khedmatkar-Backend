package com.khedmatkar.demo.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class GeoPoint {
    private Double latitude;
    private Double longitude;
}
