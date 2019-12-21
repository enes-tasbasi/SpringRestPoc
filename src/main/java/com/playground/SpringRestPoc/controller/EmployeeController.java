package com.playground.SpringRestPoc.controller;

import com.playground.SpringRestPoc.model.Company;
import com.playground.SpringRestPoc.model.Employee;
import com.playground.SpringRestPoc.repository.CompanyRepository;
import com.playground.SpringRestPoc.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/employees")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "The server is working";
    }

    @PostMapping("")
    @ResponseBody
    public Employee createEmployee(@RequestParam String companyName, @Valid @RequestBody Employee employee) {
        List<Company> companies = companyRepository.findAll();
        for(Company company : companies) {
            if(company.getName().equals(companyName)) {
                employee.setCompany(company);
                company.getEmployees().add(employee);
            }
        }
        employeeRepository.save(employee);
        return employee;
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Optional<Employee> findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping("/firstName/{firstName}")
    @ResponseBody
    public List<Employee> findAllByFirstName(@PathVariable String firstName) {
        return employeeRepository.findAllByFirstName(firstName);
    }

    @GetMapping("/lastName/{lastName}")
    @ResponseBody
    public List<Employee> findAllByLastName(@PathVariable String lastName) {
        return employeeRepository.findAllByLastName(lastName);
    }

    @GetMapping("")
    @ResponseBody
    public List<Employee> find(@RequestParam(required = false) Long id, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        System.out.println(id + " " + firstName + " " + lastName);
        List<Employee> employees = employeeRepository.findAllByFirstNameOrLastNameOrId(firstName, lastName, id);
        for(Employee employee : employees) {
            System.out.println(employee.getFirstName() + " " + employee.getLastName());
        }
        return employeeRepository.findAllByFirstNameOrLastNameOrId(firstName, lastName, id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String updateById(@PathVariable Long id, @RequestBody Employee updatedEmployee) throws IdNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        System.out.println(employee.isPresent());
        if(employee.isPresent()) {
            Employee tempEmployee = employee.get();
            if(!tempEmployee.getFirstName().equals(updatedEmployee.getFirstName()) && updatedEmployee.getFirstName() != null) {
                tempEmployee.setFirstName(updatedEmployee.getFirstName());
            }
            if(!tempEmployee.getLastName().equals(updatedEmployee.getLastName()) && updatedEmployee.getLastName() != null) {
                tempEmployee.setLastName(updatedEmployee.getLastName());
            }
            employeeRepository.save(tempEmployee);

            return "Employee updated";
        }
        return "Employee not found";
    }

    @DeleteMapping("/all")
    @ResponseBody
    public String deleteAll() {
        employeeRepository.deleteAll();
        return "Deleted all employees";
    }

    @DeleteMapping("")
    @ResponseBody
    @Transactional
    public String deleteAllByFirstNameOrLastNameOrId(@RequestParam(required = false) Long id, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        List<Employee> employees = employeeRepository.findAllByFirstNameOrLastNameOrId(firstName, lastName, id);
        employeeRepository.deleteAllByFirstNameOrLastNameOrId(firstName, lastName, id);
        return "Deleted " + employees.size() + " employees";
    }

}

class IdNotFoundException extends Exception {
    public IdNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}