package com.dishdash.restaurantservice.controller;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(path = "")
    public ResponseEntity<ResponseDto> addRestaurant(@RequestBody RequestDto requestDto){
        ResponseDto addedRestaurantResponseDto = restaurantService.addRestaurant(requestDto);
        return new ResponseEntity<>(addedRestaurantResponseDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseDto> getRestaurant(@PathVariable("id") long id){
        System.out.println(id);
        ResponseDto retrievedResponseDto = restaurantService.getRestaurant(id);
        return new ResponseEntity<>(retrievedResponseDto, HttpStatus.OK);
    }


}
