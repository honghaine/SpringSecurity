package com.restAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "student")
@Getter
@Setter
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String nationality;
    @Column
    private String city;
    @Column
    private String gender;
    @Column
    private Integer age;
    @Column
    private Double english_grade;
    @Column
    private Double math_grade;
    @Column
    private Double sciences_grade;
    @Column
    private Double language_grade;


}
