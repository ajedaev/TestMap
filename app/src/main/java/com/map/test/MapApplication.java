package com.map.test;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MapApplication extends MultiDexApplication {
    private static UmoriliApi umoriliApi;
    private Retrofit retrofit;


    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        umoriliApi = retrofit.create(UmoriliApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static UmoriliApi getApi() {
        return umoriliApi;
    }

}
