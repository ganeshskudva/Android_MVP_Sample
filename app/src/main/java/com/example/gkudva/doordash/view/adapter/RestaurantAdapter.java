package com.example.gkudva.doordash.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gkudva.doordash.R;
import com.example.gkudva.doordash.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by gkudva on 25/08/17.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Restaurant> mRestaurants;
    private Context mContext;
    private Callback callback;

    public RestaurantAdapter()
    {
        this.mRestaurants = Collections.emptyList();
    }

    public RestaurantAdapter(List<Restaurant> restaurants)
    {
        this.mRestaurants = restaurants;
    }

    public RestaurantAdapter(Context context) {
        mContext = context;
        this.mRestaurants = Collections.emptyList();
    }

    public void setRestaurants(List<Restaurant> restaurants)
    {
        this.mRestaurants = restaurants;
    }

    private Context getContext() {
        return mContext;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView statusTextView;
        public TextView descriptionTextView;
        public ImageView ivImageView;
        public ImageButton ivBtnStar;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.tvName);
            descriptionTextView = (TextView) itemView.findViewById(R.id.tvDescription);
            statusTextView = (TextView) itemView.findViewById(R.id.tvStatus);
            ivImageView = (ImageView) itemView.findViewById(R.id.ivLogo);
            ivBtnStar = (ImageButton) itemView.findViewById(R.id.btnStar);
        }
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_restaurant, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RestaurantAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Restaurant resataurant = mRestaurants.get(position);

        // Set item views based on your views and data model

        viewHolder.nameTextView.setText(resataurant.getName());
        viewHolder.descriptionTextView.setText(resataurant.getDescription());
        viewHolder.statusTextView.setText(resataurant.getStatus());
        Picasso.with(getContext())
                .load(resataurant.getImgUrl())
                .placeholder(android.R.drawable.dialog_holo_light_frame)
                .fit()
                .centerInside()
                .into(viewHolder.ivImageView);

        viewHolder.ivBtnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                {
                    callback.onItemClick(resataurant, viewHolder.itemView);
                }
            }
        });

        if (resataurant.isFavourite())
        {
            viewHolder.ivBtnStar.setImageResource(android.R.drawable.star_big_on);
        }
        else
        {
            viewHolder.ivBtnStar.setImageResource(android.R.drawable.star_big_off);
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public interface Callback {
        void onItemClick(Restaurant restaurant, View view);
    }

}
