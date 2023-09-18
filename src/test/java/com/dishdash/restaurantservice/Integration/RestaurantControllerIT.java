package com.dishdash.restaurantservice.Integration;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.entity.Restaurant;
import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.enums.Currency;
import com.dishdash.restaurantservice.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestaurantControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        restaurantRepository.deleteAll();
    }

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
        restaurantRepository.save(restaurant);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Junit test case for getRestaurant operation")
    public void givenRestaurantId_whenGetRestaurant_thenReturnResponseDto() throws Exception{

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
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // when
        ResultActions response = mockMvc.perform(get("/api/restaurants/{id}", savedRestaurant.getId()));

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Junit test case for getRestaurant operation which throws ResourceNotFound exception")
    public void givenRestaurantId_whenGetRestaurant_thenReturnResourceNotFoundException() throws Exception{

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

        // when
        ResultActions response = mockMvc.perform(get("/api/restaurants/{id}", restaurant.getId()));

        // then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Junit test case for delete Restaurant operation")
    public void givenRestaurantId_whenDeleteRestaurant_thenReturnResponseDto() throws Exception{

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
        restaurantRepository.save(restaurant);

        // when
        ResultActions response = mockMvc.perform(delete("/api/restaurants/{id}", restaurant.getId()));

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

        // when
        ResultActions response = mockMvc.perform(delete("/api/restaurants/{id}", restaurantId));

        // then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Junit test case for get all Restaurants operation with null cuisine, null sortBy, and null search.")
    public void givenNullCuisineNullSortByNullSearch_whenGetAllRestaurants_thenReturnResponseDtoList() throws Exception{

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
        restaurantRepository.save(restaurant);

        // when
        ResultActions response = mockMvc.perform(get("/api/restaurants")
                .param("cuisine", (String) null)
                .param("sortBy", (String) null)
                .param("search", (String) null)
        );

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Junit test case for get all Restaurants operation with valid cuisine, null sortBy, and null search.")
    public void givenValidCuisineNullSortByNullSearch_whenGetAllRestaurants_thenReturnResponseDtoList() throws Exception{

        // given
        String cuisine = Cuisine.INDIAN.toString();
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.INDIAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();
        restaurantRepository.save(restaurant);

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
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.INDIAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();
        restaurantRepository.save(restaurant);

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
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.INDIAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        RequestDto requestDto = RequestDto.builder()
                .name("Somesh Tacos")
                .cuisine(Cuisine.INDIAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // when
        ResultActions response = mockMvc.perform(put("/api/restaurants/{id}", savedRestaurant.getId())
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
        long nonExistingRestaurantId = 1;
        Restaurant restaurant = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.INDIAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();

        RequestDto requestDto = RequestDto.builder()
                .name("Somesh Tacos")
                .cuisine(Cuisine.INDIAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("someshvemula")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();


        // when
        ResultActions response = mockMvc.perform(put("/api/restaurants/{id}", nonExistingRestaurantId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
