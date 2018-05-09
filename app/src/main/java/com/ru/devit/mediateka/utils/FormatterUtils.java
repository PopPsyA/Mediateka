package com.ru.devit.mediateka.utils;

import android.support.v4.util.SparseArrayCompat;
import android.util.SparseArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatterUtils {

    private static final SparseArrayCompat<String> genres = new SparseArrayCompat<>();

    static {
        genres.put(28 , "боевик");
        genres.put(12 , "приключения");
        genres.put(16 , "мультфильм");
        genres.put(35 , "комедия");
        genres.put(80 , "криминал");
        genres.put(99 , "документальный");
        genres.put(18 , "драма");
        genres.put(10751 , "семейный");
        genres.put(14 , "фэнтези");
        genres.put(36 , "история");
        genres.put(27 , "ужасы");
        genres.put(10402 , "музыка");
        genres.put(10749 , "мелодрама");
        genres.put(9648 , "детектив");
        genres.put(878 , "фантастика");
        genres.put(10770 , "телевизионный фильм");
        genres.put(53 , "триллер");
        genres.put(10752 , "военный");
        genres.put(37 , "вестерн");
    }

    public static String formatDate(String releaseDate){
        if (releaseDate == null){
            return Constants.DEFAULT_VALUE;
        }
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault());

        Date date;
        try {
            date = oldDateFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return releaseDate;
        }
        return newDateFormat.format(date);
    }

    public static String formatGenres(int[] genresIds){
        StringBuilder result = new StringBuilder();
        if (genresIds.length == 0) return "";
        for (int genresId : genresIds) {
            result.append(genres.get(genresId)).append(", ");
        }
        return result.deleteCharAt(result.length() - 2).toString(); // remove last chars ", "
    }


    public static String getYearFromDate(String releaseDate) {
        if (releaseDate == null || releaseDate.length() == 0){
            return Constants.DEFAULT_VALUE;
        }
        if (releaseDate.length() == 3){
            return releaseDate.substring(0 , 3);
        }
        return releaseDate.substring(0 , 4);
    }

    public static String formatDuration(int duration){
        int hour = duration / 60;
        int minutes = duration % 60;
        return String.format(Locale.getDefault() ,"%dч %d мин" , hour , minutes);
    }

    public static String emptyValueIfNull(String value){
        if (value == null){
            value = "";
            return value;
        }
        return value;
    }
}
