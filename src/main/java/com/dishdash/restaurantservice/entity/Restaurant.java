package com.dishdash.restaurantservice.entity;

import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurants")

public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;
    private String address;
    private float rating;
    private long contactNumber;
    private String website;
    private int averageDeliveryTimeInMinutes;
    @Column(nullable = false)
    private int deliveryFee;
    @Column(nullable = false)
    private int minimumOrderAmount;
    @Enumerated(EnumType.STRING)
    private Currency currencyUsed;
}
