package com.playground.SpringRestPoc.controller;

import com.playground.SpringRestPoc.repository.Employee;
import com.playground.SpringRestPoc.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    EmployeeRepository repository;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "The server is working";
    }

    @PostMapping("")
    @ResponseBody
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        System.out.println(employee.getFirstName() + " " + employee.getLastName());
        repository.save(employee);
        return employee;
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Optional<Employee> findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping("/firstName/{firstName}")
    @ResponseBody
    public List<Employee> findAllByFirstName(@PathVariable String firstName) {
        return repository.findAllByFirstName(firstName);
    }

    @GetMapping("/lastName/{lastName}")
    @ResponseBody
    public List<Employee> findAllByLastName(@PathVariable String lastName) {
        return repository.findAllByLastName(lastName);
    }

    @GetMapping("")
    @ResponseBody
    public List<Employee> find(@RequestParam(required = false) Long id, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        System.out.println(id + " " + firstName + " " + lastName);
        List<Employee> employees = repository.findAllByFirstNameOrLastNameOrId(firstName, lastName, id);
        for(Employee employee : employees) {
            System.out.println(employee.getFirstName() + " " + employee.getLastName());
        }
        return repository.findAllByFirstNameOrLastNameOrId(firstName, lastName, id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String updateById(@PathVariable Long id, @RequestBody Employee updatedEmployee) throws IdNotFoundException {
        Optional<Employee> employee = repository.findById(id);
        System.out.println(employee.isPresent());
        if(employee.isPresent()) {
            Employee tempEmployee = employee.get();
            if(!tempEmployee.getFirstName().equals(updatedEmployee.getFirstName()) && updatedEmployee.getFirstName() != null) {
                tempEmployee.setFirstName(updatedEmployee.getFirstName());
            }
            if(!tempEmployee.getLastName().equals(updatedEmployee.getLastName()) && updatedEmployee.getLastName() != null) {
                tempEmployee.setLastName(updatedEmployee.getLastName());
            }
            repository.save(tempEmployee);

            return "Employee updated";
        }
        return "Employee not found";
    }

    @DeleteMapping("/all")
    @ResponseBody
    public String deleteAll() {
        repository.deleteAll();
        return "Deleted all employees";
    }

    @DeleteMapping("")
    @ResponseBody
    @Transactional
    public String deleteAllByFirstNameOrLastNameOrId(@RequestParam(required = false) Long id, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        List<Employee> employees = repository.findAllByFirstNameOrLastNameOrId(firstName, lastName, id);
        repository.deleteAllByFirstNameOrLastNameOrId(firstName, lastName, id);
        return "Deleted " + employees.size() + " employees";
    }

}

class IdNotFoundException extends Exception {
    public IdNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}