package com.ecommerce.projectd.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "risks")
@Getter @Setter
public class Irrigation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer previousMoistureState;

    @Column(nullable = false)
    private Integer moistureState;

    @Column(nullable = false)
    private String date;

    @ManyToOne
    private IrrigationSystem irrigationSystem;
}
