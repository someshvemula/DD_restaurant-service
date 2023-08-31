package com.dishdash.restaurantservice.service.impl;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.entity.Restaurant;
import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.enums.Currency;
import com.dishdash.restaurantservice.enums.Sort;
import com.dishdash.restaurantservice.exception.BadRequestException;
import com.dishdash.restaurantservice.exception.ResourceNotFoundException;
import com.dishdash.restaurantservice.repository.RestaurantRepository;
import com.dishdash.restaurantservice.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    @Override
    public List<ResponseDto> getAllRestaurants(String cuisine, String sortBy, String search) {

        List<Restaurant> restaurantList;
//      Filter Restaurants by Cuisine and search criteria.
        try {
            restaurantList = restaurantRepository.findRestaurants((cuisine != null) ? Cuisine.valueOf(cuisine) : null, (search != null) ? search.trim() : null);
        }
        catch (IllegalArgumentException exception){
            throw new BadRequestException("Cuisine", cuisine);
        }

//      Sort restaurants by sort criteria.
        if(sortBy != null){
            try {
                switch (Sort.valueOf(sortBy)) {
                    case NAME -> restaurantList.sort(Comparator.comparing(Restaurant::getName));
                    case RATING ->
                            restaurantList.sort(Comparator.comparingDouble(Restaurant::getRating).reversed());
                    case DELIVERY_TIME ->
                            restaurantList.sort(Comparator.comparingInt(Restaurant::getAverageDeliveryTimeInMinutes));
                    case DELIVERY_FEE ->
                            restaurantList.sort(Comparator.comparingInt(Restaurant::getDeliveryFee));
                    case MIN_ORDER_AMOUNT ->
                        restaurantList.sort(Comparator.comparingInt(Restaurant::getMinimumOrderAmount));
                }
            }
            catch (IllegalArgumentException exception){
                throw new BadRequestException("sortBy", sortBy);
            }
        }

//      Map restaurant objects to responseDto objects
        List<ResponseDto> responseDtoList = new ArrayList<>();
        for(Restaurant restaurant : restaurantList){
            responseDtoList.add(modelMapper.map(restaurant, ResponseDto.class));
        }

        return responseDtoList;
    }

    @Override
    public ResponseDto updateRestaurant(long id, RequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        restaurant.setName(requestDto.getName());
        restaurant.setCuisine(requestDto.getCuisine());
        restaurant.setAddress(requestDto.getAddress());
        restaurant.setRating(requestDto.getRating());
        restaurant.setContactNumber(requestDto.getContactNumber());
        restaurant.setWebsite(requestDto.getWebsite());
        restaurant.setAverageDeliveryTimeInMinutes(requestDto.getAverageDeliveryTimeInMinutes());
        restaurant.setDeliveryFee(requestDto.getDeliveryFee());
        restaurant.setMinimumOrderAmount(requestDto.getMinimumOrderAmount());
        restaurant.setCurrencyUsed(requestDto.getCurrencyUsed());

        return modelMapper.map(restaurantRepository.save(restaurant), ResponseDto.class);
    }
}
