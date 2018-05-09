package com.ru.devit.mediateka;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.ru.devit.mediateka.data.datasource.db.AppDatabase;
import com.ru.devit.mediateka.di.ComponentsManager;


public class MediatekaApp extends Application {

    private static ComponentsManager componentsManager;
    private static AppDatabase database;
    private static final String DATABASE_NAME = "mediateka_db";

    @Override
    public void onCreate() {
        super.onCreate();
        initComponentsManager();
        initAppComponent();
    }

    public static ComponentsManager getComponentsManager(){
        return componentsManager;
    }

    public AppDatabase getDatabaseInstance(){
        if (database == null){
            database = Room.databaseBuilder(this , AppDatabase.class , DATABASE_NAME).build();
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
