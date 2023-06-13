package com.conexia.api.notification;

public class UserNotifier extends NotifierTemplateMethod {
    protected UserNotifier(NotificationSQLRepository repository) {
        super(repository);
    }

    @Override
    public Notification make(NotificationRequestDto dto) {
        return Notification.builder().authorId(dto.getAuthorId()).targetId(dto.getTargetId()).body(dto.getBody())
                .readed(false).build();
    }
}
