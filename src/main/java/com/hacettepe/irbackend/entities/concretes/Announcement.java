package com.hacettepe.irbackend.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "announcements")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="date")
    private Date date = new Date();

    @Column(name="author")
    private String author;

    @Column(name="language")
    private String language;

    @Column(name="imagePath")
    private String imagePath;
}
