package com.conexia.api.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid AccountRequestDto dto) {
        if (accountService.existsByEmail(dto.getLogin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: E-mail in use");
        }
        accountService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable(value = "id") Long id) {
        var dto = accountService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete() {
        accountService.delete();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Valid AccountRequestDto dto) {
        var result = accountService.update(dto);
        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing to update");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
