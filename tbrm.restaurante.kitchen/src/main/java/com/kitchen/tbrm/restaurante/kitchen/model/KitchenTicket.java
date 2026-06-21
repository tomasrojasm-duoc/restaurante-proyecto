package com.kitchen.tbrm.restaurante.kitchen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kitchen_order")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KitchenTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    private String status;
    @Column(name = "estimated_time")
    private Integer estimatedTime;
    private String observations;
}