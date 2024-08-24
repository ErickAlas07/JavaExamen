package com.erick.examencursojava.service.implementations;

import com.erick.examencursojava.entity.Delivery;
import com.erick.examencursojava.entity.Product;
import com.erick.examencursojava.repository.ProductRepository;
import com.erick.examencursojava.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        Optional<Product> optionalProduct = productRepository.findByName(product.getName());
        if(optionalProduct.isPresent()){
            throw new IllegalArgumentException("A product with this name already exists.");
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product productdb = optionalProduct.orElseThrow();

            productdb.setName(product.getName());
            productdb.setCategory(product.getCategory());
            return Optional.of(productRepository.save(productdb));
        }

        return optionalProduct;
    }

    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productDb -> {
            productRepository.delete(productDb);
        });
        return optionalProduct;
    }
}
