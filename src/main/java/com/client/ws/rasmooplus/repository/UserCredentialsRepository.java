package com.client.ws.rasmooplus.repository;

import com.client.ws.rasmooplus.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByUsername(String username);

}
