package com.playground.SpringRestPoc.repository;

import com.playground.SpringRestPoc.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAll();

    Optional<Company> findById(Long id);
    Optional<Company> findByName(String name);
    List<Company> findAllByIdOrName(Long id, String name);

    void deleteAll();
    void deleteByIdOrName(Long id, String name);
}
