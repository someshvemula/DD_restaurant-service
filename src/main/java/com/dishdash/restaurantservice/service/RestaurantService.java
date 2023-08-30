package com.dishdash.restaurantservice.service;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;

public interface RestaurantService {

    ResponseDto addRestaurant(RequestDto requestDto);

    ResponseDto getRestaurant(long id);

    ResponseDto deleteRestaurant(long id);
}
