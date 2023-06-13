package com.conexia.api.notification;

public class SystemNotifier extends NotifierTemplateMethod {
    protected SystemNotifier(NotificationSQLRepository repository) {
        super(repository);
    }

    @Override
    public Notification make(NotificationRequestDto dto) {
        return Notification.builder().body(dto.getBody()).readed(false).build();
    }
}
