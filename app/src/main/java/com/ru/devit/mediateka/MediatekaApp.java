package com.ru.devit.mediateka;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.ru.devit.mediateka.data.ConnectionReceiver;
import com.ru.devit.mediateka.data.datasource.db.MediatekaDatabase;
import com.ru.devit.mediateka.di.ComponentsManager;
import com.ru.devit.mediateka.presentation.main.SyncConnectionListener;

import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;


public class MediatekaApp extends Application {

    private static ComponentsManager componentsManager;
    private static MediatekaDatabase database;
    private static final String DATABASE_NAME = "mediateka_db";

    @Override
    public void onCreate() {
        super.onCreate();
        initComponentsManager();
        initAppComponent();
        RxJavaPlugins.setErrorHandler(Functions.emptyConsumer());
    }

    public static ComponentsManager getComponentsManager(){
        return componentsManager;
    }

    public void setConnectionListener(SyncConnectionListener connectionListener){
        ConnectionReceiver.connectionListener = connectionListener;
    }

    public MediatekaDatabase getDatabaseInstance(){
        if (database == null){
            database = Room.databaseBuilder(this , MediatekaDatabase.class , DATABASE_NAME).build();
        }
        return database;
    }

    private void initComponentsManager(){
        componentsManager = new ComponentsManager(this);
    }

    private void initAppComponent(){
        componentsManager.getAppComponent();
    }

}
