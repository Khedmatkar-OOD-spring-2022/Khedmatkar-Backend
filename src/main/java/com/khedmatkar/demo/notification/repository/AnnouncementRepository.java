package com.khedmatkar.demo.notification.repository;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.notification.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByUser(User user);
}
