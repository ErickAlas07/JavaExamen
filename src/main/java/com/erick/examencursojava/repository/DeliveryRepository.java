package com.erick.examencursojava.repository;

import com.erick.examencursojava.entity.Delivery;
import com.erick.examencursojava.enums.DeliveryStatus;
import com.erick.examencursojava.enums.DeliveryType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

    @Query("SELECT d FROM Delivery d WHERE d.type = ?1 AND d.status = ?2")
    Optional<Delivery> findByTypeAndStatus(DeliveryType type, DeliveryStatus status);
}
