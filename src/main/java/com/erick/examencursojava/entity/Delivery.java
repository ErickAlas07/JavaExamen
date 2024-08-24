package com.erick.examencursojava.entity;

import com.erick.examencursojava.enums.DeliveryStatus;
import com.erick.examencursojava.enums.DeliveryType;
import com.erick.examencursojava.validation.EnumValidation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@ToString
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @EnumValidation(enumClass = DeliveryType.class, message = "Invalid value for type. Allowed values are: STANDARD_SHIPPING, EXPRESS_SHIPPING, SAME_DAY_DELIVERY")
    @Column(name = "delivery_type")
    @NotNull(message = "is mandatory")
    private DeliveryType type;

    @Enumerated(EnumType.STRING)
    @EnumValidation(enumClass = DeliveryStatus.class, message = "Invalid value for status. Allowed values are: PENDING, SHIPPED, DELIVERED, CANCELED")
    @Column(name = "delivery_status")
    @NotNull(message = "is mandatory")
    private DeliveryStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "delivery")
    @JsonIgnore
    private Set<Order> orders;

    public Delivery() {
        orders = new HashSet<>();
    }

    public Delivery(DeliveryType type, DeliveryStatus status) {
        this();
        this.type = type;
        this.status = status;
    }
}
