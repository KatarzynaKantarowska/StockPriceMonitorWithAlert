package com.kodilla.stockpricemonitorwithalert.repository;

import com.kodilla.stockpricemonitorwithalert.entity.UserEty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEty, Long> {
    Optional<UserEty> findByEmail(String email);
}
