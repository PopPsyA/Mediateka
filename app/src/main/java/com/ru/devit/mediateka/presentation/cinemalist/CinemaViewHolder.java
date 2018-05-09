package com.ru.devit.mediateka.presentation.cinemalist;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.utils.Constants;
import com.ru.devit.mediateka.utils.FormatterUtils;
import com.squareup.picasso.Picasso;

import static com.ru.devit.mediateka.utils.FormatterUtils.getYearFromDate;

public class CinemaViewHolder extends RecyclerView.ViewHolder {

    private ImageView posterImageView;
    private TextView titleTextView , ratingTextView , releaseDateTextView , descriptionTextView , genresTextView;
    private Button moreInfoButton;
    private final CinemaListPresenter presenter;

    public CinemaViewHolder(View itemView, CinemaListPresenter presenter) {
        super(itemView);
        this.presenter = presenter;
        posterImageView = itemView.findViewById(R.id.iv_poster);
        titleTextView = itemView.findViewById(R.id.tv_cinema_title);
        ratingTextView = itemView.findViewById(R.id.tv_cinema_rating);
        releaseDateTextView = itemView.findViewById(R.id.tv_release_date);
        descriptionTextView = itemView.findViewById(R.id.tv_cinema_small_description);
        genresTextView = itemView.findViewById(R.id.tv_genre);
        moreInfoButton = itemView.findViewById(R.id.btn_cinema_details);
    }

    void render(Cinema cinema , int viewHolderPosition) {
        onItemClicked(cinema.getId() , viewHolderPosition);
        renderPoster(cinema.getPosterUrl());
        render(cinema.getTitle() , cinema.getVoteAverage() ,
                    cinema.getReleaseDate() , cinema.getDescription() ,
                    cinema.getGenres());
    }
    private void onItemClicked(int cinemaId , int viewHolderPosition){
        moreInfoButton.setOnClickListener(v -> presenter.onCinemaClicked(cinemaId , viewHolderPosition));
    }

    private void renderPoster(String url){
        Picasso.with(getContext())
                .load(Constants.IMG_PATH_W185 + url)
                .placeholder(R.color.colorDarkBackground)
                .error(R.drawable.ic_cinema)
                .into(posterImageView);
    }

    private void render(String title , float rating , String date , String desc , String genres){
        titleTextView.setText(title);
        ratingTextView.setText(String.valueOf(rating));
        if (rating < 6.0){
            ratingTextView.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.rating_background_orange));
        } else {
            ratingTextView.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.rating_background_green));
        }
        releaseDateTextView.setText(getYearFromDate(date));
        descriptionTextView.setText(desc);
        genresTextView.setText(genres);
    }

    private Context getContext(){
        return itemView.getContext();
    }

}
