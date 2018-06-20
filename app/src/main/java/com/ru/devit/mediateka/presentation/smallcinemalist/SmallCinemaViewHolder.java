package com.ru.devit.mediateka.presentation.smallcinemalist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.common.OnCinemaClickListener;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;
import com.ru.devit.mediateka.utils.FormatterUtils;
import com.squareup.picasso.Picasso;

public class SmallCinemaViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageViewCinemaPoster;
    private TextView mTextViewCinemaDate , mTextViewTitle , mTextViewGenres , mTextViewCharacter;
    private final OnCinemaClickListener onCinemaClickListener;

    SmallCinemaViewHolder(View itemView , OnCinemaClickListener onCinemaClickListener) {
        super(itemView);
        this.onCinemaClickListener = onCinemaClickListener;
        mImageViewCinemaPoster = itemView.findViewById(R.id.iv_actor_detail_cinema_poster);
        mTextViewCinemaDate = itemView.findViewById(R.id.tv_actor_detail_cinema_date);
        mTextViewTitle = itemView.findViewById(R.id.tv_actor_detail_cinema_title);
        mTextViewGenres = itemView.findViewById(R.id.tv_actor_detail_cinema_genres);
        mTextViewCharacter = itemView.findViewById(R.id.tv_actor_detail_character);
    }


    void render(Cinema cinema , int viewHolderPosition) {
        onItemClicked(cinema.getId() , viewHolderPosition);
        renderImage(UrlImagePathCreator.create185pPictureUrl(cinema.getPosterUrl()));
        mTextViewCinemaDate.setText(FormatterUtils.getYearFromDate(cinema.getReleaseDate()));
        mTextViewTitle.setText(cinema.getTitle());
        mTextViewGenres.setText(FormatterUtils.formatGenres(cinema.getGenres() , getContext()));
        mTextViewCharacter.setText(TextUtils.isEmpty(cinema.getCharacter()) ? "" : getContext().getString(R.string.role , cinema.getCharacter()));
    }

    void renderImage(String url){
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.color.colorDarkBackground)
                .error(R.drawable.ic_cinema)
                .into(mImageViewCinemaPoster);
    }

    private void onItemClicked(int cinemaId , int viewHolderPosition){
        itemView.setOnClickListener(view -> onCinemaClickListener.onCinemaClicked(cinemaId , viewHolderPosition));
    }

    private Context getContext(){
        return itemView.getContext();
    }
}
