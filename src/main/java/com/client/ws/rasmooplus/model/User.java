package com.client.ws.rasmooplus.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String cpf;

    @Column(name = "dt_subscription")
    private LocalDate dtSubscription = LocalDate.now();

    @Column(name = "dt_expiration")
    private LocalDate dtExpiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriptions_type_id")
    private SubscriptionType subscriptionType;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dtSubscription=" + dtSubscription +
                ", dtExpiration=" + dtExpiration +
                ", userType=" + userType +
                ", subscriptionType=" + subscriptionType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getName(), user.getName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPhone(), user.getPhone()) && Objects.equals(getCpf(), user.getCpf()) && Objects.equals(getDtSubscription(), user.getDtSubscription()) && Objects.equals(getDtExpiration(), user.getDtExpiration()) && Objects.equals(getUserType(), user.getUserType()) && Objects.equals(getSubscriptionType(), user.getSubscriptionType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPhone(), getCpf(), getDtSubscription(), getDtExpiration(), getUserType(), getSubscriptionType());
    }
}
