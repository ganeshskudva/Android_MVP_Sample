package com.example.gkudva.doordash.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.gkudva.doordash.R;
import com.example.gkudva.doordash.constants.Constants;
import com.example.gkudva.doordash.model.Restaurant;
import com.example.gkudva.doordash.presenter.MainPresenter;
import com.example.gkudva.doordash.presenter.UpdatePresenter;
import com.example.gkudva.doordash.util.InfoMessage;
import com.example.gkudva.doordash.view.MainMvpView;
import com.example.gkudva.doordash.view.adapter.RestaurantAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainMvpView,SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView restaurantRecycleView;
    private MainPresenter presenter;
    private UpdatePresenter updatePresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private InfoMessage infoMessage;

    public final static String LIST_STATE_KEY = "recycler_list_state";
    public Parcelable listState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new MainPresenter();
        presenter.attachView(this);

        updatePresenter = new UpdatePresenter();
        updatePresenter.attach(this);

        setContentView(R.layout.activity_main);

        infoMessage = new InfoMessage(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        restaurantRecycleView = (RecyclerView) findViewById(R.id.rvRestaurants);
        setupRecyclerView(restaurantRecycleView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.loadRestaurants();

    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        updatePresenter.detach();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showRestaurants(List<Restaurant> restaurants) {
        RestaurantAdapter adapter = (RestaurantAdapter) restaurantRecycleView.getAdapter();
        adapter.setRestaurants(restaurants);
        adapter.notifyDataSetChanged();
        restaurantRecycleView.requestFocus();
        restaurantRecycleView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        RestaurantAdapter adapter = new RestaurantAdapter(this);
        adapter.setCallback(new RestaurantAdapter.Callback() {
            @Override
            public void onItemClick(Restaurant restaurant, View view) {
                if (updatePresenter.upDateRestuarantListView(restaurant, view) == false)
                {
                    infoMessage.showMessage(Constants.UPDATE_FAILED);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onRefresh() {
        presenter.loadRestaurants();
        swipeRefreshLayout.setRefreshing(false);
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        listState = layoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null)
            listState = state.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }
    }

    @Override
    public void showMessage(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        restaurantRecycleView.setVisibility(View.INVISIBLE);
        infoMessage.showMessage(message);
    }

    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        restaurantRecycleView.setVisibility(View.INVISIBLE);
    }
}
