package com.dishdash.restaurantservice.dto;


import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.enums.Currency;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private Currency currencyUsed;
}
