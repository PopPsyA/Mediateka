package com.ru.devit.mediateka.presentation.cinemalist;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.common.OnCinemaClickListener;
import com.ru.devit.mediateka.utils.FormatterUtils;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import static com.ru.devit.mediateka.utils.FormatterUtils.getYearFromDate;

public class CinemaViewHolder extends RecyclerView.ViewHolder {

    private ImageView posterImageView;
    private TextView titleTextView , ratingTextView , releaseDateTextView , descriptionTextView , genresTextView;
    private Button moreInfoButton;
    private final OnCinemaClickListener onCinemaClickListener;

    CinemaViewHolder(View itemView, OnCinemaClickListener onCinemaClickListener) {
        super(itemView);
        this.onCinemaClickListener = onCinemaClickListener;
        posterImageView = itemView.findViewById(R.id.iv_poster);
        titleTextView = itemView.findViewById(R.id.tv_cinema_title);
        ratingTextView = itemView.findViewById(R.id.tv_cinema_rating);
        releaseDateTextView = itemView.findViewById(R.id.tv_release_date);
        descriptionTextView = itemView.findViewById(R.id.tv_cinema_small_description);
        genresTextView = itemView.findViewById(R.id.tv_genre);
        moreInfoButton = itemView.findViewById(R.id.btn_cinema_details);
    }

    void render(Cinema cinema , int viewHolderPosition) {
        onMoreInfoButtonClicked(cinema.getId() , viewHolderPosition);
        onPosterClicked(cinema.getId() , viewHolderPosition);
        renderPoster(UrlImagePathCreator.create185pPictureUrl(cinema.getPosterUrl()));
        render(cinema.getTitle() , cinema.getVoteAverage() ,
                    cinema.getReleaseDate() , cinema.getDescription());
    }

    private void onMoreInfoButtonClicked(int cinemaId , int viewHolderPosition){
        moreInfoButton.setOnClickListener(v -> onCinemaClickListener.onCinemaClicked(cinemaId , viewHolderPosition));
    }

    private void onPosterClicked(int cinemaId , int viewHolderPosition){
        posterImageView.setOnClickListener(v -> onCinemaClickListener.onCinemaClicked(cinemaId , viewHolderPosition));
    }

    private void renderPoster(String url){
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.color.colorDarkBackground)
                .error(R.drawable.ic_cinema)
                .into(posterImageView);
    }

    private void render(String title , float rating , String date , String desc){
        titleTextView.setText(title);
        ratingTextView.setText(String.valueOf(rating));
        if (rating < 6.0){
            ratingTextView.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.rating_background_orange));
        } else {
            ratingTextView.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.rating_background_green));
        }
        releaseDateTextView.setText(getYearFromDate(date));
        descriptionTextView.setText(desc);
    }

    private Context getContext(){
        return itemView.getContext();
    }

}
