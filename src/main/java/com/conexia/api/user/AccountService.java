package com.conexia.api.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public AccountService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public void save(AccountRequestDto dto) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);
        var encodedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public AccountResponseDto findById(Long id) {
        var user = userRepository.findById(id).orElseThrow();
        var dto = new AccountResponseDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Transactional
    public void delete() {
        var user = authenticationService.getLoggedInUser();
        userRepository.deleteById(user.getId());
    }

    public boolean existsByEmail(String email) {
        var user = userRepository.findByLogin(email);
        return user.isPresent();
    }

    @Transactional
    public boolean update(AccountRequestDto dto) {
        var newUser = new User();
        BeanUtils.copyProperties(dto, newUser);

        var user = authenticationService.getLoggedInUser();

        if (user == newUser) {
            return false;
        }

        var oldUser = user.clone();
        BeanUtils.copyProperties(dto, user);
        var encodedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        var updatedUser = userRepository.findById(user.getId()).orElseThrow();
        return oldUser != updatedUser;
    }
}
