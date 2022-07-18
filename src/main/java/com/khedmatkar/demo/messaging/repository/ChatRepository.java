package com.khedmatkar.demo.messaging.repository;

import com.khedmatkar.demo.messaging.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
