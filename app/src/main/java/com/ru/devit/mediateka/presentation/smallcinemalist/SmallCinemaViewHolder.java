package com.ru.devit.mediateka.presentation.smallcinemalist;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.common.OnCinemaClickListener;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;
import com.ru.devit.mediateka.utils.FormatterUtils;
import com.squareup.picasso.Picasso;

public class SmallCinemaViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout mViewForeground;
    private RelativeLayout mViewBackground;
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
        mViewForeground = itemView.findViewById(R.id.view_small_cinema_foreground);
        mViewBackground = itemView.findViewById(R.id.view_small_cinema_background);
        mViewBackground.setVisibility(View.GONE);
    }

    SmallCinemaViewHolder(View itemView ,
                          OnCinemaClickListener onCinemaClickListener ,
                          @ColorRes int foregroundColor ,
                          boolean withOutBackground){
        this(itemView , onCinemaClickListener);
        if (withOutBackground){
            mViewBackground.setVisibility(View.GONE);
        } else {
            mViewBackground.setVisibility(View.VISIBLE);
        }
        mViewForeground.setBackgroundResource(foregroundColor);
    }

    void render(Cinema cinema , int viewHolderPosition) {
        onItemClicked(cinema.getId() , viewHolderPosition);
        renderImage(UrlImagePathCreator.create185pPictureUrl(cinema.getPosterUrl()));
        mTextViewCinemaDate.setText(FormatterUtils.getYearFromDate(cinema.getReleaseDate()));
        mTextViewTitle.setText(cinema.getTitle());
        mTextViewGenres.setText(FormatterUtils.formatGenres(cinema.getGenres() , getContext()));
        mTextViewCharacter.setText(TextUtils.isEmpty(cinema.getCharacter()) ? "" : getContext().getString(R.string.role , cinema.getCharacter()));
    }

    private void renderImage(String url){
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.color.colorDarkBackground)
                .error(R.drawable.ic_cinema)
                .into(mImageViewCinemaPoster);
    }

    private void onItemClicked(int cinemaId , int viewHolderPosition){
        mViewForeground.setOnClickListener(v -> onCinemaClickListener.onCinemaClicked(cinemaId , viewHolderPosition));
    }

    private Context getContext(){
        return itemView.getContext();
    }
}
