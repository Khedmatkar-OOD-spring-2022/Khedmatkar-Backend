package com.khedmatkar.demo.notification.service;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.notification.entity.Announcement;
import com.khedmatkar.demo.notification.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }


    public List<Announcement> getUserAnnouncements(User user) {
        return announcementRepository.findAllByUser(user);
    }


    public void sendAnnouncementToUser(User user, String message) {
        var announcement = Announcement.builder()
                .user(user)
                .message(message)
                .build();
        announcementRepository.save(announcement);
    }

}
