package com.playground.SpringRestPoc.controller;

import com.playground.SpringRestPoc.model.Company;
import com.playground.SpringRestPoc.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
}
