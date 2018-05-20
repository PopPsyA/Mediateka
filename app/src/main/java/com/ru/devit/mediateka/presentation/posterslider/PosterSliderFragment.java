package com.ru.devit.mediateka.presentation.posterslider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.utils.Constants;
import com.squareup.picasso.Picasso;

public class PosterSliderFragment extends Fragment {

    private static final String POSTER_URL = "poster_url";

    private ImageView mPosterImageView;

    public static PosterSliderFragment newInstance(String posterUrl){
        PosterSliderFragment fragment = new PosterSliderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POSTER_URL , posterUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_slider_poster, container , false);
        mPosterImageView = view.findViewById(R.id.iv_cinema_poster_slider);
        return view;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String imageUrl = getArguments().getString(POSTER_URL);
        renderImage(mPosterImageView ,
                imageUrl ,
                Constants.IMG_PATH_W780);
    }

    private void renderImage(ImageView image , String imgUrl , String imgResolution){
        Picasso.with(getContext())
                .load(imgResolution + imgUrl)
                .into(image);
    }
}
