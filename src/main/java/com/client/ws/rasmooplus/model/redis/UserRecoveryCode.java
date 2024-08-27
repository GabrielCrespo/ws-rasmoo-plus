package com.client.ws.rasmooplus.model.redis;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@RedisHash("recoveryCode")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRecoveryCode {

    @Id
    private String id;

    @Indexed
    @Email
    private String email;

    private String code;

    private LocalDateTime creationDate = LocalDateTime.now();

}
