package com.conexia.api.notification;

import org.springframework.stereotype.Service;

@Service
public class NotifierFacade {
    private final NotifierFactory notifierFactory;

    public NotifierFacade(NotifierFactory notifierFactory) {
        this.notifierFactory = notifierFactory;
    }

    public void notify(Long authorId, Long targetId, String body) {
        var notifier = notifierFactory.makeUserNotifier();
        var dto = NotificationRequestDto.builder().authorId(authorId).targetId(targetId).body(body).build();
        var notification = notifier.make(dto);
        notifier.emit(notification);
    }

    public void notify(String body) {
        var notifier = notifierFactory.makeSystemNotifier();
        var dto = NotificationRequestDto.builder().body(body).build();
        var notification = notifier.make(dto);
        notifier.emit(notification);
    }
}
