package com.client.ws.rasmooplus.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subscriptions_type")
public class SubscriptionType extends RepresentationModel<SubscriptionType> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptions_type_id")
    private Long id;

    private String name;

    @Column(name = "access_months")
    private Long accessMonth;

    private BigDecimal price;

    @Column(name = "product_key", unique = true)
    private String productKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionType that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getAccessMonth(), that.getAccessMonth()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getProductKey(), that.getProductKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getName(), getAccessMonth(), getPrice(), getProductKey());
    }
}
