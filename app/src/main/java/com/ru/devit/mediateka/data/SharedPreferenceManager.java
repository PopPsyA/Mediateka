package com.ru.devit.mediateka.data;

import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private final SharedPreferences preferences;
    private static final String PREF_CINEMA_SORTING_POSITION = "pref_cinema_sorting_position";

    public SharedPreferenceManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void saveCinemaSortingPosition(int position){
        edit(editor -> editor.putInt(PREF_CINEMA_SORTING_POSITION , position));
    }

    public int getCinemaSortingPosition(){
        return preferences.getInt(PREF_CINEMA_SORTING_POSITION , 0);
    }

    private void edit(Consumer<SharedPreferences.Editor> consumer){
        SharedPreferences.Editor editor = preferences.edit();
        consumer.apply(editor);
        editor.apply();
    }

    @FunctionalInterface
    private interface Consumer<T>{
        void apply(T t);
    }
}
