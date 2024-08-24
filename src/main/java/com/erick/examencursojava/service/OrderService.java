package com.erick.examencursojava.service;

import com.erick.examencursojava.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Optional<Order> findById(Long id);

    Order save(Order order);

    Optional<Order> update(Long id, Order order);

    Optional<Order> delete(Long id);

    void deleteAll();
}
