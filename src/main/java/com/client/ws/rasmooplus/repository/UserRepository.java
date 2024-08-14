package com.client.ws.rasmooplus.repository;

import com.client.ws.rasmooplus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
