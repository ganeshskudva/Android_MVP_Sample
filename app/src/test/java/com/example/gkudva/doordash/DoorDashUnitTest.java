package com.example.gkudva.doordash;

import com.example.gkudva.doordash.constants.Constants;
import com.example.gkudva.doordash.model.DoorDashService;
import com.example.gkudva.doordash.model.Restaurant;
import com.example.gkudva.doordash.presenter.MainPresenter;
import com.example.gkudva.doordash.presenter.UpdatePresenter;
import com.example.gkudva.doordash.util.MockModelFabric;
import com.example.gkudva.doordash.view.MainMvpView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gkudva on 27/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DoorDashUnitTest {

    MainPresenter mainPresenter;
    MainMvpView mainMvpView;
    UpdatePresenter updatePresenter;
    DoorDashService doorDashService;

    @Before
    public void setUp() {
        DoorDashApplication application = (DoorDashApplication) RuntimeEnvironment.application;
        doorDashService = mock(DoorDashService.class);
        // Mock the retrofit service so we don't call the API directly
        application.setDoorDashService(doorDashService);
        mainPresenter = new MainPresenter();
        updatePresenter = new UpdatePresenter();
        mainMvpView = mock(MainMvpView.class);
        when(mainMvpView.getContext()).thenReturn(application);
        mainPresenter.attachView(mainMvpView);
        updatePresenter.attach(application.getApplicationContext());
    }

    @After
    public void tearDown() {
        mainPresenter.detachView();
        updatePresenter.detach();
    }

    @Test
    public void loadRestaurantsCallsShowRestaurants() {
        List<Restaurant> restaurants = MockModelFabric.newListOfRepositories(10);

        mainPresenter.loadRestaurants(restaurants);
        verify(mainMvpView).showRestaurants(restaurants);
    }

    @Test
    public void loadRestaurantsCallsShowNoRestaurants() {
        List<Restaurant> restaurants = MockModelFabric.newListOfRepositories(0);

        mainPresenter.loadRestaurants(restaurants);
        verify(mainMvpView).showMessage(Constants.EMPTY_RES_LIST);
    }
}


