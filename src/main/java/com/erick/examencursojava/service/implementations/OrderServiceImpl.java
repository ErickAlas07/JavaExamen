package com.erick.examencursojava.service.implementations;

import com.erick.examencursojava.entity.Order;
import com.erick.examencursojava.entity.Product;
import com.erick.examencursojava.repository.OrderRepository;
import com.erick.examencursojava.repository.ProductRepository;
import com.erick.examencursojava.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Order save(Order order) {
        Set<Product> products = new HashSet<>();

        for(Product product : order.getProducts()){
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            if(optionalProduct.isPresent()){
                products.add(optionalProduct.orElseThrow());
            } else{
                throw new IllegalArgumentException("Product not found with ID: " + product.getId());
            }
        }

        order.setProducts(products);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Optional<Order> update(Long id, Order order) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order orderDb = optionalOrder.orElseThrow();

            // Actualizar los campos básicos de la orden
            orderDb.setCustomer(order.getCustomer());
            orderDb.setDelivery(order.getDelivery());

            // Cargar y validar los productos desde la base de datos
            Set<Product> loadedProducts = new HashSet<>();
            for (Product product : order.getProducts()) {
                Optional<Product> optionalProduct = productRepository.findById(product.getId());
                if (optionalProduct.isPresent()) {
                    loadedProducts.add(optionalProduct.get());
                } else {
                    throw new IllegalArgumentException("Product not found with ID: " + product.getId());
                }
            }

            // Actualizar la relación ManyToMany con los productos cargados
            orderDb.getProducts().clear();
            orderDb.getProducts().addAll(loadedProducts);

            // Guardar la orden actualizada
            return Optional.of(orderRepository.save(orderDb));
        }

        return optionalOrder;
    }


    @Override
    @Transactional
    public Optional<Order> delete(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        optionalOrder.ifPresent(orderDb -> orderRepository.delete(orderDb));
        return optionalOrder;
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
