package com.dishdash.restaurantservice.service;

import com.dishdash.restaurantservice.dto.RequestDto;
import com.dishdash.restaurantservice.dto.ResponseDto;
import com.dishdash.restaurantservice.entity.Restaurant;
import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.enums.Currency;
import com.dishdash.restaurantservice.exception.ResourceAlreadyExistsException;
import com.dishdash.restaurantservice.exception.ResourceNotFoundException;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        BDDMockito.given(restaurantRepository.findByWebsite(responseDto.getWebsite())).willReturn(Optional.empty());
        BDDMockito.given(modelMapper.map(requestDto, Restaurant.class)).willReturn(restaurant);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(responseDto);
        BDDMockito.given(restaurantRepository.save(restaurant)).willReturn(restaurant);

        //when
        ResponseDto savedResponseDto = restaurantService.addRestaurant(requestDto);

        //then
        Assertions.assertThat(savedResponseDto).isNotNull();
        Assertions.assertThat(savedResponseDto.getWebsite()).isEqualTo(requestDto.getWebsite());
    }

    @Test
    @DisplayName("Junit test for addRestaurant which throws ResourceAlreadyExists exception")
    public void givenRequestDtoObject_whenAddRestaurant_thenThrowsResourceAlreadyExistsException(){

        //given
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

        RequestDto requestDto = RequestDto.builder().build();

        //method stubbing
        BDDMockito.given(restaurantRepository.findByWebsite(requestDto.getWebsite())).willReturn(Optional.of(restaurant));

        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> restaurantService.addRestaurant(requestDto));

        //then

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

    // Junit test for getRestaurant operation which throws exception
    @Test
    @DisplayName("Junit test for getRestaurant operation which throws exception")
    public void givenNonExistingRestaurantId_whenGetRestaurant_thenThrowsResourceNotFondException(){

        // given
        long restaurantId = 2;

        // method stubbing
        BDDMockito.given(restaurantRepository.findById(restaurantId)).willReturn(Optional.empty());

        // when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> restaurantService.getRestaurant(restaurantId));

        // then

    }

    //Junit test case for delete restaurant operation.
    @Test()
    @DisplayName("Junit test case for delete restaurant operation.")
    public void givenRestaurantId_whenDeleteRestaurant_thenReturnResponseDto(){

        //given
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

        //method stubbing
        BDDMockito.given(restaurantRepository.findById(restaurantId)).willReturn(Optional.of(restaurant));
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(ResponseDto.builder().build());

        //when
        ResponseDto deletedRestaurant = restaurantService.deleteRestaurant(restaurantId);

        //then
        Assertions.assertThat(deletedRestaurant).isNotNull();
    }

    //Junit test case for delete operation which throws exception
    @Test
    @DisplayName("Junit test case for delete operation which throws exception.")
    public void givenNonExistingRestaurantId_whenDeleteRestaurant_thenThrowsResourceNotFoundException(){

        //given
        long restaurantId = 2;

        //method stubbing
        BDDMockito.given(restaurantRepository.findById(restaurantId)).willReturn(Optional.empty());

        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> restaurantService.deleteRestaurant(restaurantId));

        //then
    }

    //Junit test for updateRestaurant operation
    @Test
    @DisplayName("Junit test for updateRestaurant operation")
    public void givenRestaurantIdAndRequestDtoObject_whenUpdateRestaurant_thenReturnResponseDtoObject(){

        //given
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

        //method stubbing
        BDDMockito.given(restaurantRepository.findById(restaurantId)).willReturn(Optional.of(restaurant));
        BDDMockito.given(restaurantRepository.save(restaurant)).willReturn(restaurant);
        BDDMockito.given(modelMapper.map(restaurant, ResponseDto.class)).willReturn(ResponseDto.builder().build());

        //when
        ResponseDto responseDto = restaurantService.updateRestaurant(restaurantId, RequestDto.builder().build());

        //then
        Assertions.assertThat(responseDto).isNotNull();
    }

    //Junit test case for updateRestaurant which throws exception
    @Test
    @DisplayName("Junit test case for updateRestaurant which throws exception")
    public void givenRestaurantIdAndRequestDto_whenUpdateRestaurant_thenThrowsResourceNotFoundException(){

        //given
        long restaurantId = 2;
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

        //method stubbing
        BDDMockito.given(restaurantRepository.findById(restaurantId)).willReturn(Optional.empty());

        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> restaurantService.updateRestaurant(restaurantId, requestDto));

        //then
    }

    // Junit test case for get all restaurants operation with null cuisine, null sortBy and null search
    @Test
    @DisplayName("Junit test case for get all restaurants operation with null cuisine, null sortBy and null search")
    public void givenNullCuisineNullSortByNullSearch_whenGetAllRestaurants_thenReturnResponseDtoList(){

        // given
        String cuisine = null;
        String sortBy = null;
        String search = null;

        List<Restaurant> mockRestaurantList = Collections.singletonList(new Restaurant());

        // method stubbing
        BDDMockito.given(restaurantRepository.findRestaurants(null, search)).willReturn(mockRestaurantList);

        // when
        List<ResponseDto> restaurantList = restaurantService.getAllRestaurants(cuisine, sortBy, search);

        // then
        Assertions.assertThat(restaurantList).isNotNull();
    }

    // Junit test case for getAllRestaurants operation with valid cuisine, null sortBy, and null search
    @Test
    @DisplayName("junit test case for getAllRestaurants operation with valid cuisine, null sortBy, and null search")
    public void givenValidCuisineNullSortByNullSearch_whenGetAllRestaurants_thenReturnResponseDtoList(){

        // given
        String cuisine = "MEXICAN";
        String sortBy = null;
        String search = null;

        Restaurant restaurant1 = Restaurant.builder()
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

        Restaurant restaurant2 = Restaurant.builder()
                .name("Taco Bell")
                .cuisine(Cuisine.MEXICAN)
                .address("123 Burrito Street, Mexicoville")
                .rating(4.4F)
                .contactNumber(8023456789L)
                .website("http://www.tacobell.com")
                .averageDeliveryTimeInMinutes(30)
                .deliveryFee(6)
                .minimumOrderAmount(20)
                .currencyUsed(Currency.GBP)
                .build();

        Restaurant restaurant3 = Restaurant.builder()
                .name("El Charro")
                .cuisine(Cuisine.MEXICAN)
                .address("456 Enchilada Lane, Salsatown")
                .rating(4.8F)
                .contactNumber(8012345678L)
                .website("http://www.elcharro.com")
                .averageDeliveryTimeInMinutes(25)
                .deliveryFee(8)
                .minimumOrderAmount(30)
                .currencyUsed(Currency.GBP)
                .build();

        Restaurant restaurant4 = Restaurant.builder()
                .name("Senor Taco")
                .cuisine(Cuisine.MEXICAN)
                .address("789 Guacamole Avenue, Toppingsville")
                .rating(4.5F)
                .contactNumber(8045678901L)
                .website("http://www.senortaco.com")
                .averageDeliveryTimeInMinutes(28)
                .deliveryFee(7)
                .minimumOrderAmount(22)
                .currencyUsed(Currency.GBP)
                .build();

        Restaurant restaurant5 = Restaurant.builder()
                .name("Mexican Fiesta")
                .cuisine(Cuisine.MEXICAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("http://www.mexicanfiesta.com")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();


        List<Restaurant> mockedRestaurantList = Arrays.asList(
                restaurant1,
                restaurant2,
                restaurant3,
                restaurant4,
                restaurant5
        );

        // method stubbing
        BDDMockito.given(restaurantRepository.findRestaurants(Cuisine.valueOf(cuisine), sortBy)).willReturn(mockedRestaurantList);

        // when
        List<ResponseDto> restaurantList = restaurantService.getAllRestaurants(cuisine, sortBy, search);

        // then
        Assertions.assertThat(restaurantList).isNotNull();
        for(Restaurant restaurant : mockedRestaurantList){
            Assertions.assertThat(restaurant.getCuisine().toString()).isEqualTo(cuisine);
        }
    }

    @Test
    @DisplayName("junit test case for getAllRestaurants operation with valid cuisine, null sortBy, and valid search")
    public void givenValidCuisineNullSortByValidSearch_whenGetAllRestaurants_thenReturnResponseDtoList(){

        String cuisine = "MEXICAN";
        String sortBy = null;
        String search = "tacos";

        Restaurant restaurant1 = Restaurant.builder()
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

        Restaurant restaurant2 = Restaurant.builder()
                .name("Tacos Bell")
                .cuisine(Cuisine.MEXICAN)
                .address("123 Burrito Street, Mexicoville")
                .rating(4.4F)
                .contactNumber(8023456789L)
                .website("http://www.tacobell.com")
                .averageDeliveryTimeInMinutes(30)
                .deliveryFee(6)
                .minimumOrderAmount(20)
                .currencyUsed(Currency.GBP)
                .build();

        Restaurant restaurant3 = Restaurant.builder()
                .name("El Charro tacos")
                .cuisine(Cuisine.MEXICAN)
                .address("456 Enchilada Lane, Salsatown")
                .rating(4.8F)
                .contactNumber(8012345678L)
                .website("http://www.elcharro.com")
                .averageDeliveryTimeInMinutes(25)
                .deliveryFee(8)
                .minimumOrderAmount(30)
                .currencyUsed(Currency.GBP)
                .build();

        Restaurant restaurant4 = Restaurant.builder()
                .name("Senor Tacos")
                .cuisine(Cuisine.MEXICAN)
                .address("789 Guacamole Avenue, Toppingsville")
                .rating(4.5F)
                .contactNumber(8045678901L)
                .website("http://www.senortaco.com")
                .averageDeliveryTimeInMinutes(28)
                .deliveryFee(7)
                .minimumOrderAmount(22)
                .currencyUsed(Currency.GBP)
                .build();

        Restaurant restaurant5 = Restaurant.builder()
                .name("Mexican Fiesta tacos")
                .cuisine(Cuisine.MEXICAN)
                .address("567 Nacho Street, Salsaville")
                .rating(4.7F)
                .contactNumber(8056789012L)
                .website("http://www.mexicanfiesta.com")
                .averageDeliveryTimeInMinutes(26)
                .deliveryFee(7)
                .minimumOrderAmount(26)
                .currencyUsed(Currency.GBP)
                .build();


        List<Restaurant> mockedRestaurantList = Arrays.asList(
                restaurant1,
                restaurant2,
                restaurant3,
                restaurant4,
                restaurant5
        );

        // method stubbing
        BDDMockito.given(restaurantRepository.findRestaurants(Cuisine.valueOf(cuisine), sortBy)).willReturn(mockedRestaurantList);

        // when
        List<ResponseDto> restaurantList = restaurantService.getAllRestaurants(cuisine, sortBy, search);

        // then
        Assertions.assertThat(restaurantList).isNotNull();
        for(Restaurant restaurant : mockedRestaurantList){
            Assertions.assertThat(restaurant.getCuisine().toString()).isEqualTo(cuisine);
            Assertions.assertThat(restaurant.getName()).containsIgnoringCase(search);
        }
    }
}
