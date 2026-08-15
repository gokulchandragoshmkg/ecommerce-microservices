package com.gcg.notificationservice.repository;

import com.gcg.notificationservice.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
}