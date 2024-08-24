package com.erick.examencursojava.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "is required")
    @Column(name = "order_date")
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull(message = "is required")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    @NotNull(message = "is required")
    private Delivery delivery;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tbl_orden_producto",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
    @NotEmpty(message = "At least one product is required")
    private Set<Product> products;

    public Order() {
        products = new HashSet<>();
    }

    public Order(Date orderDate, Customer customer, Delivery delivery) {
        this();
        this.orderDate = orderDate;
        this.customer = customer;
        this.delivery = delivery;
    }
}
