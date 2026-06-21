package com.order.tbrm.restaurante.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_detail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "menu_item_id")
    private Long menuItemId;
    private Integer quantity;
    @Column(name = "unit_price")
    private Integer unitPrice;
    private Integer subtotal;
    @ManyToOne @JoinColumn(name = "order_id")
    private Order order;
}