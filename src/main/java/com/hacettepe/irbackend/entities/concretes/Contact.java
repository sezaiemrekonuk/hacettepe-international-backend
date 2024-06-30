package com.hacettepe.irbackend.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="role")
    private String role;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="categoryName")
    private String categoryName;

    @Column(name="photo")
    private byte[] photo;
}
