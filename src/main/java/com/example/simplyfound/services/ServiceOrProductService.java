package com.example.simplyfound.services;

import com.example.simplyfound.models.ServiceOrProduct;
import com.example.simplyfound.repositories.ServiceOrProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceOrProductService {
    @Autowired
    private ServiceOrProductRepository serviceOrProductRepository;

    public ServiceOrProduct save(ServiceOrProduct serviceOrProduct) {
        return serviceOrProductRepository.save(serviceOrProduct);
    }

    public List<ServiceOrProduct> findAll() {
        return serviceOrProductRepository.findAll();
    }

    public Optional<ServiceOrProduct> findById(Long id) {
        return serviceOrProductRepository.findById(id);
    }

    public List<ServiceOrProduct> findByClientJourneyId(Long clientJourneyId) {
        return serviceOrProductRepository.findByClientJourneyId(clientJourneyId);
    }

    public ServiceOrProduct update(Long id, ServiceOrProduct serviceOrProduct) {
        ServiceOrProduct existing = serviceOrProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceOrProduct not found"));
        existing.setName(serviceOrProduct.getName());
        existing.setDescription(serviceOrProduct.getDescription());
        return serviceOrProductRepository.save(existing);
    }

    public void deleteById(Long id) {
        serviceOrProductRepository.deleteById(id);
    }
}

