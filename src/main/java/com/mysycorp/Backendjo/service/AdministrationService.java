package com.mysycorp.Backendjo.service;

import com.mysycorp.Backendjo.entity.Administration;
import com.mysycorp.Backendjo.repository.AdministrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrationService {

    private final AdministrationRepository administrationRepository;

    public AdministrationService(AdministrationRepository administrationRepository) {
        this.administrationRepository = administrationRepository;
    }

    @Transactional(readOnly = true)
    public List<Administration> getAllAdministrationsWithComplexes() {
        return administrationRepository.findAllWithComplexes();
    }

    @Transactional(readOnly = true)
    public Optional<Administration> getAdministrationById(Long id) {
        return administrationRepository.findById(id);
    }

    @Transactional
    public Administration saveAdministration(Administration administration) {
        return administrationRepository.save(administration);
    }

    @Transactional
    public Optional<Administration> updateAdministration(Long id, Administration details) {
        return administrationRepository.findById(id)
                .map(admin -> {
                    admin.setNameAdministration(details.getNameAdministration());
                    admin.setAdresseAdministration(details.getAdresseAdministration());
                    admin.setRole(details.getRole());
                    if (details.getUser() != null) {
                        admin.setUser(details.getUser());
                    }
                    return administrationRepository.save(admin);
                });
    }

    @Transactional
    public boolean deleteAdministration(Long id) {
        return administrationRepository.findById(id)
                .map(admin -> {
                    administrationRepository.delete(admin);
                    return true;
                }).orElse(false);
    }
}