package com.ecommerce.projectd.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "irrigation_systems")
@Getter @Setter
public class IrrigationSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "irrigationSystem")
    private List<Irrigation> irrigation;
}
