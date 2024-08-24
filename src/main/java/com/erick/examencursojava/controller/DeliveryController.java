package com.erick.examencursojava.controller;

import com.erick.examencursojava.entity.Delivery;
import com.erick.examencursojava.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(summary = "List all deliveries", description = "Returns a list of all deliveries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public List<Delivery> list() {
        return deliveryService.findAll();
    }

    @Operation(summary = "Get delivery by ID", description = "Returns a delivery based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Delivery> opDelivery = deliveryService.findById(id);
        if (opDelivery.isPresent()) {
            return ResponseEntity.ok(opDelivery.orElseThrow());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery not found with ID: " + id);
    }

    @Operation(summary = "Register a new delivery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request. Bad Syntax."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Delivery delivery, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.save(delivery));
    }

    @Operation(summary = "Update a delivery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request. Bad Syntax."),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Delivery delivery, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Delivery> optionalDelivery = deliveryService.update(id, delivery);
        if (optionalDelivery.isPresent()) {
            return ResponseEntity.ok(optionalDelivery.orElseThrow());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery not found with ID: " + id);
    }

    @Operation(summary = "Delete a delivery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Delivery> optionalDelivery = deliveryService.delete(id);
        if (optionalDelivery.isPresent()) {
            return ResponseEntity.ok(optionalDelivery.orElseThrow());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery not found with ID: " + id);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
