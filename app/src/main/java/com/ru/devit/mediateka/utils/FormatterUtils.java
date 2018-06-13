package com.ru.devit.mediateka.utils;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;

import com.ru.devit.mediateka.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatterUtils {

    private static final SparseArrayCompat<Integer> genres = new SparseArrayCompat<>();
    public static final String DEFAULT_VALUE = "N/A";

    static {
        genres.put(28 , R.string.genre_action);
        genres.put(12 , R.string.genre_adventure);
        genres.put(16 , R.string.genre_cartoon);
        genres.put(35 , R.string.genre_comedy);
        genres.put(80 , R.string.genre_crime);
        genres.put(99 , R.string.genre_documentary);
        genres.put(18 , R.string.genre_drama);
        genres.put(10751 , R.string.genre_family);
        genres.put(14 , R.string.genre_fantasy);
        genres.put(36 , R.string.genre_history);
        genres.put(27 , R.string.genre_horror);
        genres.put(10402 , R.string.genre_music);
        genres.put(10749 , R.string.genre_melodrama);
        genres.put(9648 , R.string.genre_detective);
        genres.put(878 , R.string.genre_science_fiction);
        genres.put(10770 , R.string.genre_tv_show);
        genres.put(53 , R.string.genre_thriller);
        genres.put(10752 , R.string.genre_war);
        genres.put(37 , R.string.genre_western);
    }

    public static String formatDate(String releaseDate){
        if (releaseDate == null){
            return DEFAULT_VALUE;
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

    public static String formatGenres(int[] genresIds , Context context){
        StringBuilder result = new StringBuilder();
        if (genresIds == null || genresIds.length == 0) return "";
        for (int genresId : genresIds) {
            result.append(context.getString(genres.get(genresId))).append(", ");
        }
        return result.deleteCharAt(result.length() - 2).toString(); // remove last chars ", "
    }


    public static String getYearFromDate(String releaseDate) {
        if (releaseDate == null || releaseDate.length() == 0){
            return DEFAULT_VALUE;
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

    public static String defaultValueIfNull(String value){
        if (value == null){
            return DEFAULT_VALUE;
        }
        return value;
    }

    public static String emptyValueIfNull(String value){
        if (value == null){
            value = "";
            return value;
        }
        return value;
    }
}
