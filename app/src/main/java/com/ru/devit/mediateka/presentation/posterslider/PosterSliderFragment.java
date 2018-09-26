package com.ru.devit.mediateka.presentation.posterslider;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.presentation.common.PhotoLoader;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;
import com.ru.devit.mediateka.utils.UrlImagePathCreator.Quality;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static com.ru.devit.mediateka.utils.UrlImagePathCreator.Quality.*;

public class PosterSliderFragment extends Fragment {

    private ImageView mPosterImageView;
    private ProgressBar mProgressBarPoster;

    private final int MENU_ITEM_DOWNLOAD = 378;

    private static final String POSTER_URL = "poster_url";
    private static final String IS_BACKGROUND_POSTER = "is_background_poster";
    private static final String[] WRITE_EXTERNAL_STORAGE_PERMISSION = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

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
        setHasOptionsMenu(true);
        mPosterImageView = view.findViewById(R.id.iv_poster_slider);
        mProgressBarPoster = view.findViewById(R.id.pb_image_slider);
        //TODO VERIFY RUNTIME PERMISSION WRITE EXTERNAL STORAGE
        return view;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String imageUrl = pictureUrl();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        if (isBackgroundPoster()){
            mPosterImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Quality defaultQuality = toQuality(sharedPreferences
                    .getString(getString(R.string.pref_key_background_image_quality_type) ,
                            "1280"));
            renderImage(mPosterImageView ,
                    UrlImagePathCreator.INSTANCE.createPictureUrlFromQuality(defaultQuality , imageUrl));
        } else {
            renderImage(mPosterImageView , UrlImagePathCreator.INSTANCE.createPictureUrlFromQuality(Quality780 ,imageUrl));
        }
        verifyPermission();
    }

    private void verifyPermission() {
        int permissionStatus = ActivityCompat.checkSelfPermission(requireContext() , WRITE_EXTERNAL_STORAGE_PERMISSION[0]);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity() , WRITE_EXTERNAL_STORAGE_PERMISSION , 1);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!isBackgroundPoster()){
            menu.add(Menu.NONE , MENU_ITEM_DOWNLOAD , Menu.NONE , "Download")
                    .setIcon(R.drawable.ic_download)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ITEM_DOWNLOAD){
            downloadImage(UrlImagePathCreator.INSTANCE.createPictureUrlFromQuality(Quality780 , pictureUrl()));
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private Quality toQuality(String quality){
        return Quality.valueOf(Quality.class.getSimpleName() + quality);
    }

    private void downloadImage(String imgUrl){
        Picasso.with(requireContext())
                .load(imgUrl)
                .into(new PhotoLoader(requireContext() , pictureUrl()));
    }

    private String pictureUrl() {
        return Objects.requireNonNull(getArguments()).getString(POSTER_URL);
    }

    private boolean isBackgroundPoster(){
        return getArguments() != null && getArguments().getBoolean(IS_BACKGROUND_POSTER);
    }
}
