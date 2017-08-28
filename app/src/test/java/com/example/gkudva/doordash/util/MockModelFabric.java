package com.example.gkudva.doordash.util;

import com.example.gkudva.doordash.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gkudva on 27/08/17.
 */

public class MockModelFabric {

    public static final String IMG_URL = "https://cdn.doordash.com/media/restaurant/cover/A-Good-Morning-Cafe.png";
    public static final String DUMMY_STATUS_TYPE = "open";
    public static final String DUMMY_STATUS = "20 mins";

    public static List<Restaurant> newListOfRepositories(int numRestaurants) {
        List<Restaurant> restaurants = new ArrayList<>(numRestaurants);
        for (int i = 0; i < numRestaurants; i++) {
            restaurants.add(newRestaurant("Restaurant " + i));
        }
        return restaurants;
    }

    public static Restaurant newRestaurant(String name) {
        Random random = new Random();
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setId( Integer.toString(random.nextInt(10000)));
        restaurant.setDescription( "Description for " + name);
        restaurant.setStatusType(DUMMY_STATUS_TYPE);
        restaurant.setStatus(DUMMY_STATUS);
        restaurant.setImgUrl(IMG_URL);
        return restaurant;
    }

}
