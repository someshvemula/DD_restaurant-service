package com.dishdash.restaurantservice.dto;


import com.dishdash.restaurantservice.enums.Cuisine;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private long id;
    private String name;
    private Cuisine cuisine;
    private String address;
    private float rating;
    private long contactNumber;
    private String website;
    private int averageDeliveryTimeInMinutes;
    private int deliveryFee;
    private int minimumOrderAmount;
}
