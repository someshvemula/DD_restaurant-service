package com.dishdash.restaurantservice.controller;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private RestaurantService restaurantService;

    @PostMapping(path = "")
    public ResponseEntity<ResponseDto> addRestaurant(@RequestBody RequestDto requestDto){
        ResponseDto addedRestaurantResponseDto = restaurantService.addRestaurant(requestDto);
        return new ResponseEntity<>(addedRestaurantResponseDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseDto> getRestaurant(@PathVariable("id") long id){
        ResponseDto retrievedResponseDto = restaurantService.getRestaurant(id);
        return new ResponseEntity<>(retrievedResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteRestaurant(@PathVariable("id") long id){
        ResponseDto deletedResponseDto = restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(deletedResponseDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseDto>> getAllRestaurants(
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String search
            ){
        List<ResponseDto> responseDtoList = restaurantService.getAllRestaurants(cuisine, sortBy, search);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ResponseDto> updateRestaurant(@PathVariable("id") long id, @RequestBody RequestDto requestDto){
        ResponseDto responseDto = restaurantService.updateRestaurant(id, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(path = "/cuisines")
    public ResponseEntity<Cuisine[]> getCuisines(){
        Cuisine[] cuisines = restaurantService.getAllCuisines();
        return new ResponseEntity<>(cuisines, HttpStatus.OK);
    }

}
