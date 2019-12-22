package com.playground.SpringRestPoc.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column(name = "name", unique = true)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "company", cascade =CascadeType.REMOVE)
    private List<Employee> employees = new ArrayList<Employee>();

    public Company() {}

    public Company(String name) {
        this.name = name;
    }

}
