package com.diner.tbrm.restaurante.diner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diner")
public class Diner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String run;
    String name;
    @Column(name = "last_name")
    String lastName;
    String phone;
    String address;
    String email;
    Date birthday;
}