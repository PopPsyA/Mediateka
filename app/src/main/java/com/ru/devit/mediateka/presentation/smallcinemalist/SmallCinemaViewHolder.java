package com.ru.devit.mediateka.presentation.smallcinemalist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.utils.Constants;
import com.ru.devit.mediateka.utils.FormatterUtils;
import com.squareup.picasso.Picasso;

class SmallCinemaViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageViewCinemaPoster;
    private TextView mTextViewCinemaDate , mTextViewTitle , mTextViewGenres , mTextViewCharacter;
    private final SmallCinemasPresenter presenter;

    public SmallCinemaViewHolder(View itemView , SmallCinemasPresenter presenter) {
        super(itemView);
        this.presenter = presenter;
        mImageViewCinemaPoster = itemView.findViewById(R.id.iv_actor_detail_cinema_poster);
        mTextViewCinemaDate = itemView.findViewById(R.id.tv_actor_detail_cinema_date);
        mTextViewTitle = itemView.findViewById(R.id.tv_actor_detail_cinema_title);
        mTextViewGenres = itemView.findViewById(R.id.tv_actor_detail_cinema_genres);
        mTextViewCharacter = itemView.findViewById(R.id.tv_actor_detail_character);
    }


    public void render(Cinema cinema , int viewHolderPosition) {
        onItemClicked(cinema.getId() , viewHolderPosition);
        renderImage(cinema.getPosterUrl() , mImageViewCinemaPoster);
        mTextViewCinemaDate.setText(FormatterUtils.getYearFromDate(cinema.getReleaseDate()));
        mTextViewTitle.setText(cinema.getTitle());
        mTextViewGenres.setText(cinema.getGenres());
        mTextViewCharacter.setText(TextUtils.isEmpty(cinema.getCharacter()) ? "" : getContext().getString(R.string.role , cinema.getCharacter()));
    }

    public void renderImage(String posterUrl , ImageView imageView){
        Picasso.with(getContext())
                .load(Constants.IMG_PATH_W185 + posterUrl)
                .placeholder(R.color.colorDarkBackground)
                .error(R.drawable.ic_cinema)
                .into(imageView);
    }

    private void onItemClicked(int cinemaId , int viewHolderPosition){
        itemView.setOnClickListener(view -> presenter.onCinemaClicked(cinemaId , viewHolderPosition));
    }

    private Context getContext(){
        return itemView.getContext();
    }
}
