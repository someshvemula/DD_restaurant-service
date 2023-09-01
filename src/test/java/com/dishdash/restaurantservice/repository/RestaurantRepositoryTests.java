package com.dishdash.restaurantservice.repository;

import com.dishdash.restaurantservice.entity.Restaurant;
import com.dishdash.restaurantservice.enums.Cuisine;
import com.dishdash.restaurantservice.enums.Currency;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class RestaurantRepositoryTests {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Junit test for save restaurant method
    @Test
    @DisplayName("Junit test for save restaurant method")
    public void givenRestaurantObject_whenSave_thenReturnSavedRestaurant(){

        //given
        Restaurant restaurant = Restaurant.builder().
                name("Mehfil").
                cuisine(Cuisine.INDIAN).
                address("234 Wok Way, Noodle City").
                rating(4.3F).
                contactNumber(8500065288L).
                website("http://www.noodleoasis.com").
                averageDeliveryTimeInMinutes(20).
                deliveryFee(10).
                minimumOrderAmount(25).
                currencyUsed(Currency.INR).build();

        //when

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        //then - verify the output
        Assertions.assertThat(savedRestaurant).isNotNull();
        Assertions.assertThat(savedRestaurant.getId()).isGreaterThan(0);
    }

    //Junit test for get all restaurants operation
    @Test
    @DisplayName("Junit test for get all restaurants operation")
    public void givenRestaurantObjects_whenFindAll_thenReturnRestaurantList(){

        //given
        Restaurant firstRestaurant = Restaurant.builder().
                name("Mehfil").
                cuisine(Cuisine.INDIAN).
                address("234 Wok Way, Noodle City").
                rating(4.3F).
                contactNumber(8500065288L).
                website("http://www.noodleoasis.com").
                averageDeliveryTimeInMinutes(20).
                deliveryFee(10).
                minimumOrderAmount(25).
                currencyUsed(Currency.INR).build();

        Restaurant secondRestaurant = Restaurant.builder().
                name("Mehfil").
                cuisine(Cuisine.INDIAN).
                address("234 Wok Way, Noodle City").
                rating(4.3F).
                contactNumber(8500065288L).
                website("http://www.noodleoasis.com").
                averageDeliveryTimeInMinutes(20).
                deliveryFee(10).
                minimumOrderAmount(25).
                currencyUsed(Currency.INR).build();

        restaurantRepository.save(firstRestaurant);
        restaurantRepository.save(secondRestaurant);

        //when
        List<Restaurant> restaurantList = restaurantRepository.findAll();

        //then
        Assertions.assertThat(restaurantList).isNotNull();
        Assertions.assertThat(restaurantList.size()).isEqualTo(2);
    }

    //Junit test for get restaurant by id operation

    @Test
    @DisplayName("Junit test for get restaurant by id operation")
    public void givenRestaurantObject_whenGetRestaurantById_thenReturnRestaurantObject(){

        //given
        Restaurant restaurant = Restaurant.builder().
                name("Mehfil").
                cuisine(Cuisine.INDIAN).
                address("234 Wok Way, Noodle City").
                rating(4.3F).
                contactNumber(8500065288L).
                website("http://www.noodleoasis.com").
                averageDeliveryTimeInMinutes(20).
                deliveryFee(10).
                minimumOrderAmount(25).
                currencyUsed(Currency.INR).build();

        restaurantRepository.save(restaurant);

        //when
        Restaurant retrievedRestaurant = restaurantRepository.findById(restaurant.getId()).get();

        //then
        Assertions.assertThat(retrievedRestaurant).isNotNull();
        Assertions.assertThat(retrievedRestaurant.getWebsite()).isEqualTo(restaurant.getWebsite());
    }

    // Junit test for delete restaurant by id
    @Test
    @DisplayName("Junit test for delete restaurant by id")
    public void givenRestaurantObject_whenDeleteById_then(){
        //given
        Restaurant restaurant = Restaurant.builder().
                name("Mehfil").
                cuisine(Cuisine.INDIAN).
                address("234 Wok Way, Noodle City").
                rating(4.3F).
                contactNumber(8500065288L).
                website("http://www.noodleoasis.com").
                averageDeliveryTimeInMinutes(20).
                deliveryFee(10).
                minimumOrderAmount(25).
                currencyUsed(Currency.INR).build();
        restaurantRepository.save(restaurant);

        //when
        restaurantRepository.deleteById(restaurant.getId());

        //then
        Assertions.assertThat(restaurantRepository.findById(restaurant.getId()).isPresent()).isEqualTo(false);
    }

    //Junit test case for update restaurant operation
    @Test
    @DisplayName("Junit test case for update restaurant operation")
    public void givenRestaurantObject_whenUpdateRestaurant_thenReturnUpdatedRestaurantObject(){

        //given
        Restaurant restaurant = Restaurant.builder().
                name("Mehfil").
                cuisine(Cuisine.INDIAN).
                address("234 Wok Way, Noodle City").
                rating(4.3F).
                contactNumber(8500065288L).
                website("http://www.noodleoasis.com").
                averageDeliveryTimeInMinutes(20).
                deliveryFee(10).
                minimumOrderAmount(25).
                currencyUsed(Currency.INR).build();
        restaurantRepository.save(restaurant);

        //when
        Restaurant retrievedRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        retrievedRestaurant.setName("Bawarchi");
        retrievedRestaurant.setCuisine(Cuisine.AMERICAN);
        Restaurant updatedRestaurant = restaurantRepository.save(retrievedRestaurant);

        //then
        Assertions.assertThat(updatedRestaurant.getName()).isEqualTo("Bawarchi");
        Assertions.assertThat(updatedRestaurant.getCuisine()).isEqualTo(Cuisine.AMERICAN);
    }

    //Junit test case for findRestaurants operation.
    @Test
    @DisplayName("Junit test case for findRestaurants by cuisine operation")
    public void givenRestaurantObjects_whenFindRestaurantsByCuisine_thenReturnRestaurantList(){

        //given
        Restaurant restaurant1 = Restaurant.builder()
                .name("Spice Delight")
                .cuisine(Cuisine.INDIAN)
                .address("123 Curry Lane, Spice Town")
                .rating(4.7F)
                .contactNumber(8001234567L)
                .website("http://www.spicedelight.com")
                .averageDeliveryTimeInMinutes(25)
                .deliveryFee(8)
                .minimumOrderAmount(30)
                .currencyUsed(Currency.INR)
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("Pasta Palace")
                .cuisine(Cuisine.ITALIAN)
                .address("456 Noodle Road, Pasta City")
                .rating(4.5F)
                .contactNumber(8009876543L)
                .website("http://www.pastapalace.com")
                .averageDeliveryTimeInMinutes(30)
                .deliveryFee(7)
                .minimumOrderAmount(35)
                .currencyUsed(Currency.CAD)
                .build();

        Restaurant restaurant3 = Restaurant.builder()
                .name("Sushi Haven")
                .cuisine(Cuisine.JAPANESE)
                .address("789 Sashimi Street, Sushi Town")
                .rating(4.9F)
                .contactNumber(8012345678L)
                .website("http://www.sushihaven.com")
                .averageDeliveryTimeInMinutes(22)
                .deliveryFee(5)
                .minimumOrderAmount(40)
                .currencyUsed(Currency.USD)
                .build();

        Restaurant restaurant4 = Restaurant.builder()
                .name("Burger Bistro")
                .cuisine(Cuisine.AMERICAN)
                .address("101 Burger Boulevard, Patty City")
                .rating(4.2F)
                .contactNumber(8023456789L)
                .website("http://www.burgerbistro.com")
                .averageDeliveryTimeInMinutes(28)
                .deliveryFee(6)
                .minimumOrderAmount(20)
                .currencyUsed(Currency.USD)
                .build();

        Restaurant restaurant5 = Restaurant.builder()
                .name("Taco Fiesta")
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

        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);
        restaurantRepository.save(restaurant3);
        restaurantRepository.save(restaurant4);
        restaurantRepository.save(restaurant5);

        //when
        List<Restaurant> restaurantList = restaurantRepository.findRestaurants(Cuisine.AMERICAN, null);

        //then
        Assertions.assertThat(restaurantList).isNotNull();
        Assertions.assertThat(restaurantList.isEmpty()).isEqualTo(false);
        Assertions.assertThat(restaurantList.size()).isEqualTo(1);
        Assertions.assertThat(restaurantList.get(0).getCuisine()).isEqualTo(Cuisine.AMERICAN);
    }

    @Test
    @DisplayName("Junit test case for findRestaurants by search operation")
    public void givenRestaurantObjects_whenFindRestaurantsBySearch_thenReturnRestaurantList(){

        //given
        Restaurant restaurant1 = Restaurant.builder()
                .name("Spice Delight")
                .cuisine(Cuisine.INDIAN)
                .address("123 Curry Lane, Spice Town")
                .rating(4.7F)
                .contactNumber(8001234567L)
                .website("http://www.spicedelight.com")
                .averageDeliveryTimeInMinutes(25)
                .deliveryFee(8)
                .minimumOrderAmount(30)
                .currencyUsed(Currency.INR)
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("Pasta Palace")
                .cuisine(Cuisine.ITALIAN)
                .address("456 Noodle Road, Pasta City")
                .rating(4.5F)
                .contactNumber(8009876543L)
                .website("http://www.pastapalace.com")
                .averageDeliveryTimeInMinutes(30)
                .deliveryFee(7)
                .minimumOrderAmount(35)
                .currencyUsed(Currency.CAD)
                .build();

        Restaurant restaurant3 = Restaurant.builder()
                .name("Sushi Haven")
                .cuisine(Cuisine.JAPANESE)
                .address("789 Sashimi Street, Sushi Town")
                .rating(4.9F)
                .contactNumber(8012345678L)
                .website("http://www.sushihaven.com")
                .averageDeliveryTimeInMinutes(22)
                .deliveryFee(5)
                .minimumOrderAmount(40)
                .currencyUsed(Currency.USD)
                .build();

        Restaurant restaurant4 = Restaurant.builder()
                .name("Burger Bistro")
                .cuisine(Cuisine.AMERICAN)
                .address("101 Burger Boulevard, Patty City")
                .rating(4.2F)
                .contactNumber(8023456789L)
                .website("http://www.burgerbistro.com")
                .averageDeliveryTimeInMinutes(28)
                .deliveryFee(6)
                .minimumOrderAmount(20)
                .currencyUsed(Currency.USD)
                .build();

        Restaurant restaurant5 = Restaurant.builder()
                .name("Taco Fiesta")
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

        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);
        restaurantRepository.save(restaurant3);
        restaurantRepository.save(restaurant4);
        restaurantRepository.save(restaurant5);

        //when
        List<Restaurant> restaurantList = restaurantRepository.findRestaurants(null, "Taco");

        //then
        Assertions.assertThat(restaurantList).isNotNull();
        Assertions.assertThat(restaurantList.isEmpty()).isEqualTo(false);
        Assertions.assertThat(restaurantList.size()).isEqualTo(1);
        Assertions.assertThat(restaurantList.get(0).getName()).isEqualTo("Taco Fiesta");
        Assertions.assertThat(restaurantList.get(0).getCuisine()).isEqualTo(Cuisine.MEXICAN);
    }

    @Test
    @DisplayName("Junit test case for findRestaurants by cuisine and search operation")
    public void givenRestaurantObjects_whenFindRestaurantsByCuisineAndSearch_thenReturnRestaurantList(){

        //given
        Restaurant restaurant1 = Restaurant.builder()
                .name("Spice Delight")
                .cuisine(Cuisine.INDIAN)
                .address("123 Curry Lane, Spice Town")
                .rating(4.7F)
                .contactNumber(8001234567L)
                .website("http://www.spicedelight.com")
                .averageDeliveryTimeInMinutes(25)
                .deliveryFee(8)
                .minimumOrderAmount(30)
                .currencyUsed(Currency.INR)
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("Pasta Palace")
                .cuisine(Cuisine.INDIAN)
                .address("456 Noodle Road, Pasta City")
                .rating(4.5F)
                .contactNumber(8009876543L)
                .website("http://www.pastapalace.com")
                .averageDeliveryTimeInMinutes(30)
                .deliveryFee(7)
                .minimumOrderAmount(35)
                .currencyUsed(Currency.CAD)
                .build();

        Restaurant restaurant3 = Restaurant.builder()
                .name("Sushi Haven")
                .cuisine(Cuisine.INDIAN)
                .address("789 Sashimi Street, Sushi Town")
                .rating(4.9F)
                .contactNumber(8012345678L)
                .website("http://www.sushihaven.com")
                .averageDeliveryTimeInMinutes(22)
                .deliveryFee(5)
                .minimumOrderAmount(40)
                .currencyUsed(Currency.USD)
                .build();

        Restaurant restaurant4 = Restaurant.builder()
                .name("Burger Bistro")
                .cuisine(Cuisine.AMERICAN)
                .address("101 Burger Boulevard, Patty City")
                .rating(4.2F)
                .contactNumber(8023456789L)
                .website("http://www.burgerbistro.com")
                .averageDeliveryTimeInMinutes(28)
                .deliveryFee(6)
                .minimumOrderAmount(20)
                .currencyUsed(Currency.USD)
                .build();

        Restaurant restaurant5 = Restaurant.builder()
                .name("Taco Fiesta")
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

        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);
        restaurantRepository.save(restaurant3);
        restaurantRepository.save(restaurant4);
        restaurantRepository.save(restaurant5);

        //when
        List<Restaurant> restaurantList = restaurantRepository.findRestaurants(Cuisine.INDIAN, "Sushi");

        //then
        Assertions.assertThat(restaurantList).isNotNull();
        Assertions.assertThat(restaurantList.isEmpty()).isEqualTo(false);
        Assertions.assertThat(restaurantList.size()).isEqualTo(1);
        Assertions.assertThat(restaurantList.get(0).getName()).isEqualTo("Sushi Haven");
        Assertions.assertThat(restaurantList.get(0).getCuisine()).isEqualTo(Cuisine.INDIAN);
    }

    @Test
    @DisplayName("Junit test case for findRestaurants by cuisine and search operation")
    public void givenRestaurantObjects_whenFindRestaurantsBySearchAndCuisine_thenReturnRestaurantList(){

        //given
        Restaurant restaurant1 = Restaurant.builder()
                .name("Spice Delight")
                .cuisine(Cuisine.INDIAN)
                .address("123 Curry Lane, Spice Town")
                .rating(4.7F)
                .contactNumber(8001234567L)
                .website("http://www.spicedelight.com")
                .averageDeliveryTimeInMinutes(25)
                .deliveryFee(8)
                .minimumOrderAmount(30)
                .currencyUsed(Currency.INR)
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("Pasta Palace")
                .cuisine(Cuisine.INDIAN)
                .address("456 Noodle Road, Pasta City")
                .rating(4.5F)
                .contactNumber(8009876543L)
                .website("http://www.pastapalace.com")
                .averageDeliveryTimeInMinutes(30)
                .deliveryFee(7)
                .minimumOrderAmount(35)
                .currencyUsed(Currency.CAD)
                .build();

        Restaurant restaurant3 = Restaurant.builder()
                .name("Sushi Haven")
                .cuisine(Cuisine.INDIAN)
                .address("789 Sashimi Street, Sushi Town")
                .rating(4.9F)
                .contactNumber(8012345678L)
                .website("http://www.sushihaven.com")
                .averageDeliveryTimeInMinutes(22)
                .deliveryFee(5)
                .minimumOrderAmount(40)
                .currencyUsed(Currency.USD)
                .build();

        Restaurant restaurant4 = Restaurant.builder()
                .name("Burger Bistro")
                .cuisine(Cuisine.AMERICAN)
                .address("101 Burger Boulevard, Patty City")
                .rating(4.2F)
                .contactNumber(8023456789L)
                .website("http://www.burgerbistro.com")
                .averageDeliveryTimeInMinutes(28)
                .deliveryFee(6)
                .minimumOrderAmount(20)
                .currencyUsed(Currency.USD)
                .build();

        Restaurant restaurant5 = Restaurant.builder()
                .name("Taco Fiesta")
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

        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);
        restaurantRepository.save(restaurant3);
        restaurantRepository.save(restaurant4);
        restaurantRepository.save(restaurant5);

        //when
        List<Restaurant> restaurantList = restaurantRepository.findRestaurants(Cuisine.CHINESE, "Burger");

        //then
        Assertions.assertThat(restaurantList).isNotNull();
        Assertions.assertThat(restaurantList.isEmpty()).isEqualTo(true);
        Assertions.assertThat(restaurantList.size()).isEqualTo(0);
    }
}