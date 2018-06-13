package com.ru.devit.mediateka.presentation.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.utils.FormatterUtils;

public class CinemaHeaderView extends ConstraintLayout {

    private View rootView;
    private TextView releaseDateTextView , durationTextView , titleTextView , genresTextView;
    private ProgressBar mProgressBar;

    public CinemaHeaderView(Context context) {
        super(context);
        init(context);
    }

    public CinemaHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CinemaHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void render(Cinema cinema){
        titleTextView.setText(cinema.getTitle());
        releaseDateTextView.setText(FormatterUtils.getYearFromDate(cinema.getReleaseDate()));
        durationTextView.setText(FormatterUtils.formatDuration(cinema.getDuration()));
        genresTextView.setText(FormatterUtils.formatGenres(cinema.getGenres() , getContext()));
    }

    public void startProgress(){
        mProgressBar.setVisibility(VISIBLE);
    }

    public void hideProgress(){
        mProgressBar.setVisibility(GONE);
    }

    private void init(Context context){
        if (rootView == null){
            rootView = inflate(context , R.layout.cinema_detail_header_view, this);
        }
        releaseDateTextView = rootView.findViewById(R.id.cinema_release_date);
        durationTextView = rootView.findViewById(R.id.cinema_duration);
        titleTextView = rootView.findViewById(R.id.cinema_title);
        genresTextView = rootView.findViewById(R.id.cinema_genres);
        mProgressBar = rootView.findViewById(R.id.progressBar);
    }
}
