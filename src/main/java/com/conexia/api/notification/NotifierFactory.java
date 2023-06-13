package com.conexia.api.notification;

import org.springframework.stereotype.Service;

@Service
public class NotifierFactory {
    private final NotificationSQLRepository repository;

    public NotifierFactory(NotificationSQLRepository repository) {
        this.repository = repository;
    }

    public NotifierTemplateMethod makeSystemNotifier() {
        return new SystemNotifier(repository);
    }

    public NotifierTemplateMethod makeUserNotifier() {
        return new UserNotifier(repository);
    }
}
