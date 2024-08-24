package com.erick.examencursojava.repository;

import com.erick.examencursojava.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
