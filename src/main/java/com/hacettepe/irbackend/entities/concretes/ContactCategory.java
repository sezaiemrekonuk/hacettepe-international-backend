package com.hacettepe.irbackend.entities.concretes;

import jakarta.persistence.*;

import java.util.List;

public class ContactCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="categoryName")
    private String categoryName;


    @Column(name="contacts")
    @OneToMany(mappedBy = "contactCategory")
    private List<Contact> contacts;

}
