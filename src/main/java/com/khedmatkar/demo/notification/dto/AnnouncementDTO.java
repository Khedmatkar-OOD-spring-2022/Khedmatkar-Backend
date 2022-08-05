package com.khedmatkar.demo.notification.dto;

import com.khedmatkar.demo.notification.entity.Announcement;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@NoArgsConstructor
@SuperBuilder
public class AnnouncementDTO {
    public LocalDateTime creation;
    public String message;


    public static AnnouncementDTO from(Announcement announcement) {
        return AnnouncementDTO.builder()
                .creation(announcement.getCreation())
                .message(announcement.getMessage())
                .build();
    }
}
