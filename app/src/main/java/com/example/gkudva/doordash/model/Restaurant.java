package com.example.gkudva.doordash.model;

import com.example.gkudva.doordash.util.RestaurantsTimeHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gkudva on 25/08/17.
 */

public class Restaurant {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("cover_img_url")
    @Expose
    private String cover_img_url;

    @SerializedName("status_type")
    @Expose
    private String status_type;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("id")
    @Expose
    private String id;

    private boolean isFavourite;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }

    public Restaurant(String name, String description, String imgUrl, String statusType, String status) {
        this.name = name;
        this.description = description;
        this.cover_img_url = imgUrl;
        this.status_type = statusType;
        this.status = status;
    }

    public Restaurant() {
        this.isFavourite = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImgUrl(String imgUrl) {
        this.cover_img_url = imgUrl;
    }

    public void setStatusType(String statusType) {
        this.status_type = statusType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getName() {

        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return cover_img_url;
    }

    public String getStatusType() {
        return status_type;
    }

    public String getStatus() {

        if (status.contains("Pre-order"))
        {
            status = RestaurantsTimeHelper.getTimeDiff(status);
        }
        else if (status_type.contains("unavailable"))
        {
            status = "Closed";
        }
        return status;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
}
