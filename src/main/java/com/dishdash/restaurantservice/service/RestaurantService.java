package com.dishdash.restaurantservice.service;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.enums.Cuisine;

import java.util.List;

public interface RestaurantService {

    ResponseDto addRestaurant(RequestDto requestDto);

    ResponseDto getRestaurant(long id);

    ResponseDto deleteRestaurant(long id);

    List<ResponseDto> getAllRestaurants(String cuisine, String sortBy, String search);

    ResponseDto updateRestaurant(long id, RequestDto requestDto);

    Cuisine[] getAllCuisines();

}
