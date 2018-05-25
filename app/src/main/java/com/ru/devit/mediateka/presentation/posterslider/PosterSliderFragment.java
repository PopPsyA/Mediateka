package com.ru.devit.mediateka.presentation.posterslider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PosterSliderFragment extends Fragment {

    private static final String POSTER_URL = "poster_url";
    private static final String IS_BACKGROUND_POSTER = "is_background_poster";

    private ImageView mPosterImageView;
    private ProgressBar mProgressBarPoster;

    public static PosterSliderFragment newInstance(String posterUrl , boolean isBackgroundPoster){
        PosterSliderFragment fragment = new PosterSliderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POSTER_URL , posterUrl);
        bundle.putBoolean(IS_BACKGROUND_POSTER , isBackgroundPoster);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_slider_poster, container , false);
        mPosterImageView = view.findViewById(R.id.iv_poster_slider);
        mProgressBarPoster = view.findViewById(R.id.pb_image_slider);
        return view;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String imageUrl = getArguments().getString(POSTER_URL);
        if (isBackgroundPoster()){
            mPosterImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            renderImage(mPosterImageView , UrlImagePathCreator.create1280pPictureUrl(imageUrl));
        } else {
            renderImage(mPosterImageView , UrlImagePathCreator.create780pPictureUrl(imageUrl));
        }
    }

    private void renderImage(ImageView image , String imgUrl){
        Picasso.with(getContext())
                .load(imgUrl)
                .placeholder(R.color.colorWhite)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBarPoster.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        mProgressBarPoster.setVisibility(View.GONE);
                    }
                });
    }

    private boolean isBackgroundPoster(){
        return getArguments().getBoolean(IS_BACKGROUND_POSTER);
    }
}
