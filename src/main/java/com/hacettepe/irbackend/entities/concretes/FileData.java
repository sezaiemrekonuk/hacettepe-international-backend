package com.hacettepe.irbackend.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    private String name;
    private String type;

    @Column(name="filePath")
    private String filePath;
}
