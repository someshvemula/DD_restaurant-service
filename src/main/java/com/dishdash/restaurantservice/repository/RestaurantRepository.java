package com.dishdash.restaurantservice.repository;

import com.dishdash.restaurantservice.entity.Restaurant;
import com.dishdash.restaurantservice.enums.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT r FROM Restaurant r WHERE " +
            "(:cuisine IS NULL OR r.cuisine = :cuisine) " +
            "AND (:search IS NULL OR r.name LIKE %:search%)")
    List<Restaurant> findRestaurants(Cuisine cuisine, String search);

    Optional<Restaurant> findByWebsite(String website);
}
