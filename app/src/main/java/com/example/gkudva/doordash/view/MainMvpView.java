package com.example.gkudva.doordash.view;

import com.example.gkudva.doordash.model.Restaurant;

import java.util.List;

/**
 * Created by gkudva on 25/08/17.
 */

public interface MainMvpView extends MvpView {

    void showRestaurants(List<Restaurant> restaurants);

    void showMessage(String message);

    void showProgressIndicator();
}
