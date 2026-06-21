package com.waiter.tbrm.restaurante.waiter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Waiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String run;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String phone;
    private String email;
    private String shift;
    private String status;
}