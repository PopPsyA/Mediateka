package com.ru.devit.mediateka.models;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class IntArrayConverter {

    @TypeConverter
    public static String toJson(final int[] ints){
        Gson gson = new Gson();
        return gson.toJson(ints);
    }

    @TypeConverter
    public static int[] fromJson(final String val){
        final Type type = new TypeToken<int[]>(){}.getType();
        return new Gson().fromJson(val , type);
    }
}
