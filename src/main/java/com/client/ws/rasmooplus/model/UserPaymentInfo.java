package com.client.ws.rasmooplus.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_payment_info")
public class UserPaymentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_payment_info_id")
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_expiration_month")
    private Long cardExpirationMonth;

    @Column(name = "card_expiration_year")
    private Long cardExpirationYear;

    @Column(name = "card_security_code")
    private String cardSecurityCode;

    private BigDecimal price;

    private Long instalments;

    @Column(name = "dt_payment")
    private LocalDate dtPayment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
