package com.khedmatkar.demo.messaging.repository;

import com.khedmatkar.demo.messaging.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
