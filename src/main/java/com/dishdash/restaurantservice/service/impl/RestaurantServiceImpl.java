package com.dishdash.restaurantservice.service.impl;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.entity.Restaurant;
import com.dishdash.restaurantservice.exception.ResourceNotFoundException;
import com.dishdash.restaurantservice.repository.RestaurantRepository;
import com.dishdash.restaurantservice.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseDto addRestaurant(RequestDto requestDto) {
        Restaurant restaurant = modelMapper.map(requestDto, Restaurant.class);
        Restaurant addedRestaurant = restaurantRepository.save(restaurant);
        return modelMapper.map(addedRestaurant, ResponseDto.class);
    }

    @Override
    public ResponseDto getRestaurant(long id) {
        Restaurant retrievedRestaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        return modelMapper.map(retrievedRestaurant, ResponseDto.class);
    }

    @Override
    public ResponseDto deleteRestaurant(long id) {
        Restaurant deletedRestaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        restaurantRepository.deleteById(id);
        return modelMapper.map(deletedRestaurant, ResponseDto.class);
    }
}
