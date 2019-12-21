package com.playground.SpringRestPoc.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees = new ArrayList<Employee>();

    public Company() {}

    public Company(String name) {
        this.name = name;
    }

}
