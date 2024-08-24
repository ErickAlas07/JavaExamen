package com.erick.examencursojava.service.implementations;

import com.erick.examencursojava.entity.Customer;
import com.erick.examencursojava.entity.Delivery;
import com.erick.examencursojava.repository.DeliveryRepository;
import com.erick.examencursojava.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Delivery> findAll() {
        return (List<Delivery>) deliveryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Delivery> findById(Long id) {
        return deliveryRepository.findById(id);
    }

    @Override
    @Transactional
    public Delivery save(Delivery delivery) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findByTypeAndStatus(delivery.getType(), delivery.getStatus());
        if(optionalDelivery.isPresent()){
            throw new IllegalArgumentException("A delivery with this type and status already exists.");
        }
        return deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public Optional<Delivery> update(Long id, Delivery delivery) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
        if(optionalDelivery.isPresent()){
            Delivery deliveryDb = optionalDelivery.orElseThrow();

            // Verificar si la nueva combinación de type y status ya existe en otro registro
            Optional<Delivery> existingDelivery = deliveryRepository.findByTypeAndStatus(delivery.getType(), delivery.getStatus());
            if (existingDelivery.isPresent() && !existingDelivery.get().getId().equals(id)) {
                throw new IllegalArgumentException("A delivery with this type and status already exists.");
            }

            deliveryDb.setType(delivery.getType());
            deliveryDb.setStatus(delivery.getStatus());

            return Optional.of(deliveryRepository.save(deliveryDb));
        }

        return optionalDelivery;
    }

    @Override
    @Transactional
    public Optional<Delivery> delete(Long id) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
        optionalDelivery.ifPresent(deliveryDb -> {
            deliveryRepository.delete(deliveryDb);
        });
        return optionalDelivery;
    }
}
