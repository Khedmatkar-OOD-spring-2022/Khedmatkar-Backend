package com.khedmatkar.demo.notification.service;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.notification.entity.Announcement;
import com.khedmatkar.demo.notification.repository.AnnouncementRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final EmailService emailService;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               EmailService emailService) {
        this.announcementRepository = announcementRepository;
        this.emailService = emailService;
    }


    public List<Announcement> getUserAnnouncements(User user) {
        return announcementRepository.findAllByUser(user);
    }

    public void sendAnnouncementToUser(User user, AnnouncementMessage message, Object... message_params) {
        var announcement = Announcement.builder()
                .user(user)
                .subject(message.getSubject())
                .message(message.getMessage().formatted(message_params))
                .build();
        announcementRepository.save(announcement);
    }

    public void sendEmailToUser(User user, AnnouncementMessage message, Object... message_params) {
        emailService.send(user.getEmail(), message.getSubject(),
                message.getMessage().formatted(message_params));
    }

    public void sendAnnouncementWithEmailToUser(User user, AnnouncementMessage message, Object... message_params) {
        sendAnnouncementToUser(user, message, message_params);
        sendEmailToUser(user, message, message_params);
    }

}
