package com.map.test;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.map.test.utils.SharedPreferencesFile;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private final String TAG = "myLogs";
    private CameraPosition mCameraPosition;
    private MarkerOptions mMarkerOptions;
    private LiveData<String> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferencesFile.initSharedReferences(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MapApplication.getApi().getData("bash", 50).enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                //Данные успешно пришли, но надо проверить response.body() на null
                String str = "";
            }
            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                //Произошла ошибка
            }
        });

        liveData = DataController.getInstance().getData();
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String value) {
                ((TextView) findViewById(R.id.location_tv)).setText(value);
            }
        });

    }

    private void init() {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mCameraPosition = map.getCameraPosition();
//                ((TextView) findViewById(R.id.location_tv)).setText(mCameraPosition.toString());
            }
        });
       map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
           @Override
           public void onMapClick(LatLng latLng) {
               map.clear();
               map.addMarker(new MarkerOptions()
                       .position(latLng)
                       .title("Hello world"));
           }
       });

    }


    public void onClickTest(View view) {
//        if (map.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
//            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        else
//            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mapFragment.getMapAsync(this);
        DataController.getInstance().setValue("QQQ");
    }

    public void onClickLocation(View view) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(SharedPreferencesFile.getLocationPosX(), SharedPreferencesFile.getLocationPosY()))
                .zoom(SharedPreferencesFile.getLocationPosZoom())
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mCameraPosition != null) {
            SharedPreferencesFile.setLocationPosX((float) mCameraPosition.target.latitude);
            SharedPreferencesFile.setLocationPosY((float) mCameraPosition.target.longitude);
            SharedPreferencesFile.setLocationPosZoom(mCameraPosition.zoom);
        }

        DataController.getInstance().setValue("PAUSE");
    }



}
