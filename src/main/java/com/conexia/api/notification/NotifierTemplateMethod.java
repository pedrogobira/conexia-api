package com.conexia.api.notification;

public abstract class NotifierTemplateMethod {
    private final NotificationSQLRepository repository;

    protected NotifierTemplateMethod(NotificationSQLRepository repository) {
        this.repository = repository;
    }

    public abstract Notification make(NotificationRequestDto dto);

    public void emit(Notification notification) {
        repository.save(notification);
    }
}
