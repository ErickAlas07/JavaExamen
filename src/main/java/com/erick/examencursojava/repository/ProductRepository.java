package com.erick.examencursojava.repository;

import com.erick.examencursojava.entity.Delivery;
import com.erick.examencursojava.entity.Product;
import com.erick.examencursojava.enums.DeliveryStatus;
import com.erick.examencursojava.enums.DeliveryType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Optional<Product> findByName(String name);
}
