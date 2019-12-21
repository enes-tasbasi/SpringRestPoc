package com.playground.SpringRestPoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAll();

    Optional<Employee> findById(Long id);
    Optional<Employee> findByLastName(String lastName);
    Optional<Employee> findByFirstName(String firstName);

    List<Employee> findAllByFirstName(String firstName);
    List<Employee> findAllByLastName(String lastName);
    List<Employee> findAllByFirstNameOrLastNameOrId(String firstName, String lastName, Long id);

    void deleteAll();
    void deleteAllByFirstNameOrLastNameOrId(String firstName, String lastName, Long id);
}
