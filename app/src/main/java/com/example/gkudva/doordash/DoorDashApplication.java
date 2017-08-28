package com.example.gkudva.doordash;

import android.app.Application;
import android.content.Context;

import com.example.gkudva.doordash.model.DoorDashService;

/**
 * Created by gkudva on 27/08/17.
 */

public class DoorDashApplication extends Application {

    private DoorDashService doorDashService;

    public static DoorDashApplication get(Context context) {
        return (DoorDashApplication) context.getApplicationContext();
    }

    public DoorDashService getDoorDashService() {
        if (doorDashService == null) {
            doorDashService = DoorDashService.Factory.create();
        }
        return doorDashService;
    }

    //For setting mocks during testing
    public void setDoorDashService(DoorDashService doorDashService) {
        this.doorDashService = doorDashService;
    }
}
