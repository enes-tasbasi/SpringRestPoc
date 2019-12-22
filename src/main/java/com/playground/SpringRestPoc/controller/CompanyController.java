package com.playground.SpringRestPoc.controller;

import com.playground.SpringRestPoc.model.Company;
import com.playground.SpringRestPoc.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/companies")
public class CompanyController {
    @Autowired
    CompanyRepository repository;

    @PostMapping("")
    @ResponseBody
    public Company createCompany(@Valid @RequestBody Company company) {
        return repository.save(company);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Company> findAll() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Optional<Company> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public Optional<Company> getByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @GetMapping("")
    @ResponseBody
    public List<Company> find(@RequestParam(required = false) Long id, @RequestParam(required = false) String name) {
        return repository.findAllByIdOrName(id, name);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String updateById(@PathVariable Long id, @Valid @RequestBody Company updatedCompany) {
        Optional<Company> company = repository.findById(id);
        if(company.isPresent()) {
            Company tempCompany = company.get();
            if(!tempCompany.getName().equals(updatedCompany.getName())) {
                tempCompany.setName(updatedCompany.getName());
            }
            repository.save(tempCompany);

            return "Company updated";
        }
        return "Company not found";
    }

    @DeleteMapping("/all")
    @ResponseBody
    public String deleteAll() {
        repository.deleteAll();
        return "Deleted all companies";
    }

    @DeleteMapping
    @ResponseBody
    @Transactional
    public String deleteByIdOrName(@RequestParam(required = false) Long id, @RequestParam(required = false) String name) {
        List<Company> companies = repository.findAllByIdOrName(id, name);
        repository.deleteByIdOrName(id, name);
        return "Deleted " + companies.size() + " companies";
    }


}
