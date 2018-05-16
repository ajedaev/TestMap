package com.map.test;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * Created by kuleshov on 14.5.18.
 */

public class DataController {
    private static DataController instance = new DataController();
    private MutableLiveData<String> liveData = new MutableLiveData<>();

    private DataController() {

    }

    public static DataController getInstance() {
        return instance;
    }

    LiveData<String> getData() {
        return liveData;
    }

    void setValue(String value) {
        liveData.setValue(value);
    }
}
