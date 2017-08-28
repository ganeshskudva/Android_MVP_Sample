package com.example.gkudva.doordash.presenter;

import android.database.Cursor;
import android.util.Log;

import com.example.gkudva.doordash.constants.Constants;
import com.example.gkudva.doordash.model.DoorDashService;
import com.example.gkudva.doordash.model.Restaurant;
import com.example.gkudva.doordash.model.RestaurantsDBHelper;
import com.example.gkudva.doordash.view.MainMvpView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gkudva on 25/08/17.
 */

public class MainPresenter implements Presenter<MainMvpView> {

    private MainMvpView mainMvpView;
    private List<Restaurant> restaurantsList;
    private RestaurantsDBHelper mysqlite;

    @Override
    public void attachView(MainMvpView view) {
        this.mainMvpView = view;
    }

    @Override
    public void detachView() {
        this.mainMvpView = null;
    }
    
    public void loadRestaurants()
    {
        mainMvpView.showProgressIndicator();

        mysqlite = RestaurantsDBHelper.getInstance(mainMvpView.getContext());
        restaurantsList = new ArrayList<Restaurant>();
        Cursor result = mysqlite.selectAllData();

        if (result.moveToFirst() == false) {
            DoorDashService service = DoorDashService.Factory.create();
            final Call<List<Restaurant>> call =
                    service.getResponse();

            call.enqueue(new Callback<List<Restaurant>>() {
                @Override
                public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                    if (response.isSuccessful()) {
                        restaurantsList = response.body();
                        mainMvpView.showRestaurants(restaurantsList);
                        mysqlite.addRestaurants(restaurantsList);
                        Log.d(Constants.MAIN_PRESENTER_TAG, "posts loaded from API");
                    } else {
                        Log.d(Constants.MAIN_PRESENTER_TAG, response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                    Log.d(Constants.MAIN_PRESENTER_TAG, call.toString());
                }
            });
        }
        else
        {
            restaurantsList.clear();
            do
            {
                Restaurant res = new Restaurant();
                res.setId(Integer.toString(result.getInt(0)));
                res.setName(result.getString(1));
                res.setDescription(result.getString(2));
                res.setImgUrl(result.getString(3));
                res.setStatusType(result.getString(4));
                res.setStatus(result.getString(5));
                if (result.getInt(6) == 0)
                {
                    res.setFavourite(false);
                }
                else
                {
                    res.setFavourite(true);
                }

                if (res.isFavourite())
                {
                    restaurantsList.add(0, res);
                }
                else
                {
                    restaurantsList.add(res);
                }
            }while (result.moveToNext());
            mainMvpView.showRestaurants(restaurantsList);
        }
    }

    public void loadRestaurants(List<Restaurant> restaurants)
    {
        if (!restaurants.isEmpty())
        {
            mainMvpView.showRestaurants(restaurants);
        }
        else
        {
            mainMvpView.showMessage(Constants.EMPTY_RES_LIST);
        }
    }
}
