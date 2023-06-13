package com.conexia.api.notification;

import com.conexia.api.exception.AuthorizationException;
import com.conexia.api.user.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotificationService {
    private final NotificationSQLRepository repository;
    private final AuthenticationService authenticationService;

    public NotificationService(NotificationSQLRepository repository, AuthenticationService authenticationService) {
        this.repository = repository;
        this.authenticationService = authenticationService;
    }

    public List<NotificationResponseDto> show() {
        var user = authenticationService.getLoggedInUser();
        var notifications = repository.findByTargetId(user.getId());
        return notifications.stream().filter(notification -> notification.getReaded().equals(false))
                .map(notification -> NotificationResponseDto.builder().authorId(notification.getAuthorId())
                        .body(notification.getBody()).readed(notification.getReaded()).build()).toList();
    }

    public void read(Long id) {
        var user = authenticationService.getLoggedInUser();
        var optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new NoSuchElementException();
        }

        var notification = optional.get();

        if (!user.getId().equals(notification.getTargetId())) {
            throw new AuthorizationException();
        }

        if (notification.getReaded().equals(true)) {
            throw new NoSuchElementException();
        }

        notification.setReaded(true);
        repository.save(notification);
    }
}
