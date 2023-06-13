package com.conexia.api.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationSQLRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTargetId(Long id);
}
