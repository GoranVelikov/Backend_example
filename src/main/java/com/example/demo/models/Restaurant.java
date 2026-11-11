package com.example.demo.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "RESTAURANTS")
public class Restaurant {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "restaurant_seq_generator"
    )
    @SequenceGenerator(
            name = "restaurant_seq_generator",
            sequenceName = "RESTAURANT_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    public Restaurant() {}

    public Restaurant( String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;
}