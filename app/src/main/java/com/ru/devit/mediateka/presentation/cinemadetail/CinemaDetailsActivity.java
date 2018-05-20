package com.ru.devit.mediateka.presentation.cinemadetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.CinemaDetailModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.posterslider.PosterSliderActivity;
import com.ru.devit.mediateka.presentation.common.ViewPagerAdapter;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.actorlist.ActorsFragment;
import com.ru.devit.mediateka.presentation.widget.CinemaHeaderView;
import com.ru.devit.mediateka.utils.AnimUtils;
import com.ru.devit.mediateka.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class CinemaDetailsActivity extends BaseActivity implements CinemaDetailPresenter.View{

    private static final String CINEMA_ID = "cinema_id";

    private ImageView mBackgroundPoster;
    private ImageView mSmallPosterImageView;
    private CinemaHeaderView mCinemaHeaderView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ProgressBar mProgressBarBackgroundImage;
    private AppBarLayout mAppBarLayout;

    @Inject CinemaDetailPresenter presenter;

    public static Intent makeIntent(Context context , int cinemaId){
        Intent intent = new Intent(context , CinemaDetailsActivity.class);
        intent.putExtra(CINEMA_ID , cinemaId);
        return intent;
    }

    @Override
    protected void onStop() {
        mAppBarLayout.addOnOffsetChangedListener(null);
        super.onStop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema_detail;
    }

    @Override
    protected void initViews(){
        mBackgroundPoster = findViewById(R.id.iv_cinema_detail_background_poster);
        mSmallPosterImageView = findViewById(R.id.iv_cinema_small_poster);
        mCinemaHeaderView = findViewById(R.id.cinema_header_view);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mProgressBarBackgroundImage = findViewById(R.id.progressBackgroundImage);
        mAppBarLayout = findViewById(R.id.app_bar_cinema);
    }

    @Override
    public void showLoading() {
        mCinemaHeaderView.startProgress();
    }

    @Override
    public void hideLoading() {
        mCinemaHeaderView.hideProgress();
    }

    @Override
    public void showCinemaDetail(final Cinema cinema) {
        AnimUtils.startRevealAnimation(mBackgroundPoster);
        renderImage(cinema.getPosterPath() , mSmallPosterImageView , true , Constants.IMG_PATH_W185);
        renderImage(cinema.getBackdropPath() , mBackgroundPoster , false , Constants.IMG_PATH_W1280);
        mCinemaHeaderView.render(cinema);
        mSmallPosterImageView.setOnClickListener(v -> presenter.onSmallPosterClicked(cinema.getPosterUrls()));
        addOffsetChangeListener(mAppBarLayout , cinema.getTitle());
        setUpViewPager(mViewPager , mTabLayout , cinema);
    }

    @Override
    public void showListPosters(List<String> posterUrls){
        Intent intent = PosterSliderActivity.makeIntent(this , posterUrls);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_home : {
                navigateToMainActivity(this);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initToolbar(){
        super.initToolbar();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initDagger(){
        MediatekaApp.getComponentsManager()
                .plusCinemaComponent()
                .plusCinemaDetailComponent(new CinemaDetailModule())
                .inject(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initPresenter(){
        presenter.setView(this);
        presenter.setCinemaId(getIntent().getExtras().getInt(CINEMA_ID));
        presenter.initialize();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        MediatekaApp.getComponentsManager().clearCinemaComponent();
        super.onDestroy();
    }

    private void setUpViewPager(ViewPager viewPager , TabLayout tabLayout , Cinema cinema) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CinemaDetailContentFragment.newInstance(cinema) , getString(R.string.message_info));
        adapter.addFragment(ActorsFragment.newInstance(cinema.getActors()) , String
                .format(Locale.getDefault() , "%s (%d)" , getString(R.string.actors) , cinema.getActors().size()));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void renderImage(String url , ImageView image , boolean itSmallPoster , String imgPath){
        Picasso.with(CinemaDetailsActivity.this)
                .load(imgPath + url)
                .placeholder(itSmallPoster ? R.color.colorPosterBackground : R.color.colorWhite)
                .error(R.drawable.ic_cinema)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        mSmallPosterImageView.setBackgroundColor(ContextCompat.getColor(CinemaDetailsActivity.this , android.R.color.transparent));
                        if (itSmallPoster){
                            final Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                            Palette.from(bitmap)
                                    .generate(palette -> {
                                        Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                                        Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                                        if (mutedSwatch != null) {
                                            mTabLayout.setBackgroundColor(mutedSwatch.getRgb());
                                        }
                                        if (darkMutedSwatch != null){
                                            getCollapsingToolbarLayout().setContentScrimColor(darkMutedSwatch.getRgb());
                                            getCollapsingToolbarLayout().setBackgroundColor(darkMutedSwatch.getRgb());
                                        }
                                    });
                        } else {
                            mProgressBarBackgroundImage.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        mProgressBarBackgroundImage.setVisibility(View.GONE);
                    }
                });
    }
}
