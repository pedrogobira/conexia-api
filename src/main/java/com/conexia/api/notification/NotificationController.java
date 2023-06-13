package com.conexia.api.notification;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> show() {
        return ResponseEntity.status(HttpStatus.OK).body(service.show());
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<Object> like(@PathVariable(value = "id") Long id) {
        service.read(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
