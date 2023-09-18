package com.dishdash.restaurantservice.controller;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.entity.Restaurant;
import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.enums.Currency;
import com.dishdash.restaurantservice.exception.BadRequestException;
import com.dishdash.restaurantservice.exception.ResourceAlreadyExistsException;
import com.dishdash.restaurantservice.exception.ResourceNotFoundException;
import com.dishdash.restaurantservice.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class RestaurantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    //
    @Test
    @DisplayName("Junit test case for addRestaurant operation.")
    public void givenRequestDto_whenAddRestaurant_thenReturnResponseDto() throws Exception{

        // given
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.MEXICAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        RequestDto requestDto = new RequestDto();
        ResponseDto responseDto = new ResponseDto();

        // Method stubbing

        BDDMockito.given(modelMapper.map(requestDto, Restaurant.class)).willReturn(restaurant);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);
        BDDMockito.given(restaurantService.addRestaurant(ArgumentMatchers.any(RequestDto.class)))
                .willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("Junit test case for addRestaurant operation which throws ResourceAlreadyExists exception.")
    public void givenRequestDto_whenAddRestaurant_thenThrowsResourceAlreadyExistsException() throws Exception{

        // given
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.MEXICAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        RequestDto requestDto = new RequestDto();
        ResponseDto responseDto = new ResponseDto();

        // Method stubbing

        BDDMockito.given(modelMapper.map(requestDto, Restaurant.class)).willReturn(restaurant);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);
        BDDMockito.given(restaurantService.addRestaurant(ArgumentMatchers.any(RequestDto.class)))
                .willThrow(new ResourceAlreadyExistsException("Restaurant", "website", restaurant.getWebsite()));

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isConflict());

    }

    //Junit test case for getRestaurant operation.
    @Test
    @DisplayName("Junit test case for getRestaurant operation")
    public void givenRestaurantId_whenGetRestaurant_thenReturnResponseDto() throws Exception{

        // given
        long restaurantId = 2;
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.MEXICAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        RequestDto requestDto = new RequestDto();
        ResponseDto responseDto = new ResponseDto();

        // method stubbing
        BDDMockito.given(restaurantService.getRestaurant(ArgumentMatchers.any(long.class)))
                .willReturn(responseDto);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);



        // when
        ResultActions response = mockMvc.perform(get("/api/restaurants/{id}", restaurantId));

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Junit test case for getRestaurant operation which throws ResourceNotFound exception")
    public void givenRestaurantId_whenGetRestaurant_thenReturnResourceNotFoundException() throws Exception{

        // given
        long restaurantId = 2;
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.MEXICAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        RequestDto requestDto = new RequestDto();
        ResponseDto responseDto = new ResponseDto();

        // method stubbing
        BDDMockito.given(restaurantService.getRestaurant(ArgumentMatchers.any(long.class)))
                .willThrow(new ResourceNotFoundException("Restaurant", "id", restaurantId));
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(get("/api/restaurants/{id}", restaurantId));

        // then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Junit test case for delete Restaurant operation")
    public void givenRestaurantId_whenDeleteRestaurant_thenReturnResponseDto() throws Exception{

        // given
        long restaurantId = 2;
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.MEXICAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        RequestDto requestDto = new RequestDto();
        ResponseDto responseDto = new ResponseDto();

        // method stubbing
        BDDMockito.given(restaurantService.deleteRestaurant(ArgumentMatchers.any(long.class)))
                        .willReturn(responseDto);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(delete("/api/restaurants/{id}", restaurantId));

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Junit test case for delete Restaurant operation which throws exception")
    public void givenRestaurantId_whenDeleteRestaurant_thenThrowsResourceNotFoundException() throws Exception{

        // given
        long restaurantId = 2;
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.MEXICAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        RequestDto requestDto = new RequestDto();
        ResponseDto responseDto = new ResponseDto();

        // method stubbing
        BDDMockito.given(restaurantService.deleteRestaurant(ArgumentMatchers.any(long.class)))
                .willThrow(new ResourceNotFoundException("Restaurant", "id", restaurantId));
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(delete("/api/restaurants/{id}", restaurantId));

        // then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Junit test case for get all Restaurants operation with null cuisine, null sortBy, and null search.")
    public void givenNullCuisineNullSortByNullSearch_whenGetAllRestaurants_thenReturnResponseDtoList() throws Exception{

        // given
        List<ResponseDto> responseDtoList = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto();
        Restaurant restaurant = new Restaurant();

        // method stubbing
        BDDMockito.given(restaurantService.getAllRestaurants(null, null, null))
                .willReturn(responseDtoList);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(get("/api/restaurants"));

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Junit test case for get all Restaurants operation with valid cuisine, null sortBy, and null search.")
    public void givenValidCuisineNullSortByNullSearch_whenGetAllRestaurants_thenReturnResponseDtoList() throws Exception{

        // given
        String cuisine = Cuisine.INDIAN.toString();
        List<ResponseDto> responseDtoList = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto();
        Restaurant restaurant = new Restaurant();

        // method stubbing
        BDDMockito.given(restaurantService.getAllRestaurants(cuisine, null, null))
                .willReturn(responseDtoList);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(get("/api/restaurants")
                .param("cuisine", cuisine)
                .param("sortBy", (String) null)
                .param("search", (String) null)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Junit test case for get all Restaurants operation with invalid cuisine, null sortBy, and null search.")
    public void givenInvalidCuisineNullSortByNullSearch_whenGetAllRestaurants_thenThrowsBadRequestException() throws Exception{

        // given
        String cuisine = "randomString";
        ResponseDto responseDto = new ResponseDto();
        Restaurant restaurant = new Restaurant();

        // method stubbing
        BDDMockito.given(restaurantService.getAllRestaurants(cuisine, null, null))
                .willThrow(new BadRequestException("Cuisine", cuisine));
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(get("/api/restaurants")
                .param("cuisine", cuisine)
                .param("sortBy", (String) null)
                .param("search", (String) null)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Junit test case for update Restaurant operation with invalid cuisine, null sortBy, and null search.")
    public void givenRestaurantIdAndRequestDto_whenUpdateRestaurant_thenReturnResponseDto() throws Exception{

        // given
        long restaurantId = 2;
        RequestDto requestDto = new RequestDto();
        ResponseDto responseDto = new ResponseDto();
        Restaurant restaurant = new Restaurant();

        // method stubbing
        BDDMockito.given(restaurantService.updateRestaurant(restaurantId, requestDto))
                .willReturn(responseDto);
        BDDMockito.given(modelMapper.map(requestDto, Restaurant.class)).willReturn(restaurant);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(put("/api/restaurants/{id}", restaurantId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Junit test case for update Restaurant operation which throws exception")
    public void givenRestaurantIdAndRequestDto_whenUpdateRestaurant_thenThrowsResourceNotFoundException() throws Exception{

        // given
        long restaurantId = 2;
        RequestDto requestDto = new RequestDto();
        ResponseDto responseDto = new ResponseDto();
        Restaurant restaurant = new Restaurant();
        requestDto.setId(restaurantId);

        // method stubbing
        BDDMockito.given(restaurantService.updateRestaurant(ArgumentMatchers.eq(restaurantId), ArgumentMatchers.any(RequestDto.class)))
                .willThrow(new ResourceNotFoundException("Restaurant", "id", restaurantId));
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);

        // when
        ResultActions response = mockMvc.perform(put("/api/restaurants/{id}", restaurantId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
