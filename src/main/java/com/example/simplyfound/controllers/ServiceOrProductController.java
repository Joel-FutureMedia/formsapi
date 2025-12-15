package com.example.simplyfound.controllers;

import com.example.simplyfound.models.ServiceOrProduct;
import com.example.simplyfound.services.ServiceOrProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services-or-products")
public class ServiceOrProductController {
    @Autowired
    private ServiceOrProductService serviceOrProductService;

    @PostMapping
    public ServiceOrProduct create(@RequestBody ServiceOrProduct serviceOrProduct) {
        return serviceOrProductService.save(serviceOrProduct);
    }

    @GetMapping
    public List<ServiceOrProduct> findAll() {
        return serviceOrProductService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ServiceOrProduct> findById(@PathVariable Long id) {
        return serviceOrProductService.findById(id);
    }

    @GetMapping("/client-journey/{clientJourneyId}")
    public List<ServiceOrProduct> findByClientJourneyId(@PathVariable Long clientJourneyId) {
        return serviceOrProductService.findByClientJourneyId(clientJourneyId);
    }

    @PutMapping("/{id}")
    public ServiceOrProduct update(@PathVariable Long id, @RequestBody ServiceOrProduct serviceOrProduct) {
        return serviceOrProductService.update(id, serviceOrProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        serviceOrProductService.deleteById(id);
    }
}

