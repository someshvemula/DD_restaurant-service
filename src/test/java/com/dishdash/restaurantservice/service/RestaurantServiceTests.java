package com.dishdash.restaurantservice.service;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.entity.Restaurant;
import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.enums.Currency;
import com.dishdash.restaurantservice.repository.RestaurantRepository;
import com.dishdash.restaurantservice.service.impl.RestaurantServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RestaurantServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    //Junit test case for addRestaurant operation
    @Test
    @DisplayName("Junit test case for addRestaurant operation")
    public void givenRequestDtoObject_whenAddRestaurant_thenReturnResponseDtoObject(){

        //given
        RequestDto requestDto = RequestDto.builder()
                .name("Somesh Tacos")
                .cuisine(Cuisine.MEXICAN)
                .address("234 Tostada Avenue, Taco Town")
                .rating(4.6F)
                .contactNumber(8034567890L)
                .website("http://www.tacofiesta.com")
                .averageDeliveryTimeInMinutes(27)
                .deliveryFee(7)
                .minimumOrderAmount(25)
                .currencyUsed(Currency.GBP)
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Somesh Tacos")
                .cuisine(Cuisine.MEXICAN)
                .address("234 Tostada Avenue, Taco Town")
                .rating(4.6F)
                .contactNumber(8034567890L)
                .website("http://www.tacofiesta.com")
                .averageDeliveryTimeInMinutes(27)
                .deliveryFee(7)
                .minimumOrderAmount(25)
                .currencyUsed(Currency.GBP)
                .build();

        ResponseDto responseDto = ResponseDto.builder()
                .name("Somesh Tacos")
                .cuisine(Cuisine.MEXICAN)
                .address("234 Tostada Avenue, Taco Town")
                .rating(4.6F)
                .contactNumber(8034567890L)
                .website("http://www.tacofiesta.com")
                .averageDeliveryTimeInMinutes(27)
                .deliveryFee(7)
                .minimumOrderAmount(25)
                .currencyUsed(Currency.GBP)
                .build();

        //Method stubbing
        BDDMockito.given(modelMapper.map(requestDto, Restaurant.class)).willReturn(restaurant);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);
        BDDMockito.given(restaurantRepository.save(restaurant)).willReturn(restaurant);

        //when
        ResponseDto savedResponseDto = restaurantService.addRestaurant(requestDto);

        //then
        Assertions.assertThat(savedResponseDto).isNotNull();
        Assertions.assertThat(savedResponseDto.getWebsite()).isEqualTo(requestDto.getWebsite());
    }

    // Junit test for getRestaurant operation
    @Test
    @DisplayName("Junit test for getRestaurant operation")
    public void givenRestaurantId_whenGetRestaurant_thenReturnResponseDto(){
        // given
        long restaurantId = 2;
        Restaurant restaurant = Restaurant.builder()
                .name("Somesh Tacos")
                .cuisine(Cuisine.MEXICAN)
                .address("234 Tostada Avenue, Taco Town")
                .rating(4.6F)
                .contactNumber(8034567890L)
                .website("http://www.tacofiesta.com")
                .averageDeliveryTimeInMinutes(27)
                .deliveryFee(7)
                .minimumOrderAmount(25)
                .currencyUsed(Currency.GBP)
                .build();

        // method stubbing
        BDDMockito.given(restaurantRepository.findById(restaurantId)).willReturn(Optional.of(restaurant));
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(new ResponseDto());

        // when
        ResponseDto responseDto = restaurantService.getRestaurant(restaurantId);

        // then
        Assertions.assertThat(responseDto).isNotNull();

    }

}
