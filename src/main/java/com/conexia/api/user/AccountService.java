package com.conexia.api.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;

    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(AccountRequestDto dto) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);
        userRepository.save(user);
    }

    public AccountResponseDto findById(Long id) {
        var user = userRepository.findById(id).orElseThrow();
        var dto = new AccountResponseDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Transactional
    public boolean update(Long id, AccountRequestDto dto) {
        var newUser = new User();
        BeanUtils.copyProperties(dto, newUser);
        var optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return false;
        }
        var user = optionalUser.get();
        if (user == newUser) {
            return false;
        }
        var oldUser = user.clone();
        BeanUtils.copyProperties(dto, user);
        userRepository.save(user);
        var updatedUser = userRepository.findById(id).orElseThrow();
        return oldUser != updatedUser;
    }
}
