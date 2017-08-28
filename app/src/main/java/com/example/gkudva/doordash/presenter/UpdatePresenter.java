package com.example.gkudva.doordash.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.example.gkudva.doordash.R;
import com.example.gkudva.doordash.model.Restaurant;
import com.example.gkudva.doordash.model.RestaurantsDBHelper;

/**
 * Created by gkudva on 27/08/17.
 */

public class UpdatePresenter implements UIPresenter {

    private Context context;
    private RestaurantsDBHelper mysqlite;

    @Override
    public void attach(Context context) {
        this.context = context;
        mysqlite = RestaurantsDBHelper.getInstance(context);
    }

    @Override
    public void detach() {
        this.context = null;
    }

    public boolean upDateRestuarantListView(Restaurant restaurant, View view)
    {
        boolean updateSuccess = false;

        if (restaurant != null || view != null)
        {
            boolean fav = restaurant.isFavourite();
            restaurant.setFavourite(!fav);
            mysqlite.updateFavChoice(restaurant);
            ImageButton imgBtn = (ImageButton) view.findViewById(R.id.btnStar);

            if (!fav)
                imgBtn.setImageResource(android.R.drawable.star_big_on);
            else
                imgBtn.setImageResource(android.R.drawable.star_big_off);

            updateSuccess = true;
        }

        return updateSuccess;
    }
}
