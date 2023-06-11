package com.conexia.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
