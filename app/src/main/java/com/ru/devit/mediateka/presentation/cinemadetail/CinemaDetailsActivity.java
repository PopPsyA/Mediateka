package com.ru.devit.mediateka.presentation.cinemadetail;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.cinemadetail.CinemaDetailModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.posterslider.PosterSliderActivity;
import com.ru.devit.mediateka.presentation.common.ViewPagerAdapter;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.actorlist.ActorsFragment;
import com.ru.devit.mediateka.presentation.posterslider.PosterSliderAdapter;
import com.ru.devit.mediateka.presentation.widget.CinemaHeaderView;
import com.ru.devit.mediateka.presentation.widget.IndicatorView;
import com.ru.devit.mediateka.utils.AnimUtils;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class CinemaDetailsActivity extends BaseActivity implements CinemaDetailPresenter.View{

    private static final String CINEMA_ID = "cinema_id";

    private ViewPager mViewPagerBackgroundPoster;
    private ViewPager mViewPagerCinemaInfo;
    private ImageView mSmallPosterImageView;
    private CinemaHeaderView mCinemaHeaderView;
    private IndicatorView mIndicatorView;
    private TabLayout mTabLayout;
    private AppBarLayout mAppBarLayout;
    private PosterSliderAdapter mBackgroundPosterSliderAdapter;
    private FloatingActionButton mFABCinemaMenu;
    private LinearLayout mLinearLayoutAddToFavourite;
    private View mViewForegroundStub;

    @Inject CinemaDetailPresenter presenter;

    public static Intent makeIntent(Context context , int cinemaId){
        Intent intent = new Intent(context , CinemaDetailsActivity.class);
        intent.putExtra(CINEMA_ID , cinemaId);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFABCinemaMenu.setOnClickListener(v -> presenter.onFABCinemaMenuClicked());
        mViewForegroundStub.setOnClickListener(v -> presenter.onForegroundViewClicked());
        mLinearLayoutAddToFavourite.setOnClickListener(v -> presenter.onAddFavouriteCinemaClicked());
    }

    @Override
    protected void onStop() {
        mAppBarLayout.addOnOffsetChangedListener(null);
        mFABCinemaMenu.setOnClickListener(null);
        mViewForegroundStub.setOnClickListener(null);
        super.onStop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema_detail;
    }

    @Override
    protected void initViews(){
        mViewPagerBackgroundPoster = findViewById(R.id.vp_cinema_detail_background_poster);
        mSmallPosterImageView = findViewById(R.id.iv_cinema_small_poster);
        mCinemaHeaderView = findViewById(R.id.cinema_header_view);
        mIndicatorView = findViewById(R.id.cinema_indicator_view);
        mViewPagerCinemaInfo = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mAppBarLayout = findViewById(R.id.app_bar_cinema);
        mFABCinemaMenu = findViewById(R.id.fab_cinema_menu);
        mLinearLayoutAddToFavourite = findViewById(R.id.ll_add_to_favourite);
        mViewForegroundStub = findViewById(R.id.view_foreground_stub);
        ViewCompat.setTranslationZ(findViewById(R.id.layout_cinema_fab_menu) , 1f);
        mLinearLayoutAddToFavourite.setScaleX(0);
        mLinearLayoutAddToFavourite.setScaleY(0);
        mViewForegroundStub.setAlpha(0.8f);
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
        AnimUtils.startRevealAnimation(mViewPagerBackgroundPoster);
        mBackgroundPosterSliderAdapter = new PosterSliderAdapter(getSupportFragmentManager() , cinema.getBackdropUrls() , true);
        mViewPagerBackgroundPoster.setAdapter(mBackgroundPosterSliderAdapter);
        mIndicatorView.setUpWithViewPager(mViewPagerBackgroundPoster);
        renderImage(UrlImagePathCreator.create185pPictureUrl(cinema.getPosterUrl()) , mSmallPosterImageView);
        mCinemaHeaderView.render(cinema);
        mSmallPosterImageView.setOnClickListener(v -> presenter.onSmallPosterClicked(cinema.getPosterUrls()));
        addOffsetChangeListener(mAppBarLayout , cinema.getTitle());
        setUpViewPager(mViewPagerCinemaInfo, mTabLayout , cinema);
    }

    @Override
    public void showListPosters(List<String> posterUrls){
        ActivityOptionsCompat activityOptions =  ActivityOptionsCompat.makeSceneTransitionAnimation(
                this ,
                mSmallPosterImageView ,
                getString(R.string.transition_cinema_poster_image));
        Intent intent = PosterSliderActivity.makeIntent(this , posterUrls , getString(R.string.transition_cinema_poster_image));
        ActivityCompat.startActivity(this , intent , activityOptions.toBundle());
    }

    @Override
    public void showFABCinemaMenu(){
        mLinearLayoutAddToFavourite.setVisibility(View.VISIBLE);
        mViewForegroundStub.setVisibility(View.VISIBLE);
        ViewCompat.setTranslationZ(mViewForegroundStub , 0.9f);
        mFABCinemaMenu.animate()
                .rotationBy(180);
        mLinearLayoutAddToFavourite.animate()
                .alphaBy(1)
                .scaleX(1)
                .scaleY(1);
    }

    @Override
    public void hideFABCinemaMenu(){
        mFABCinemaMenu.animate().rotationBy(-180);
        mViewForegroundStub.setVisibility(View.GONE);
        ViewCompat.setTranslationZ(mViewForegroundStub , 0f);
        mLinearLayoutAddToFavourite.animate()
                .alphaBy(0)
                .scaleX(0)
                .scaleY(0);
    }

    @Override
    public void showSuccessfullyFavouriteCinemaAdded(){
        Toast.makeText(this , getString(R.string.message_successfully_favourite_cinema_added) , Toast.LENGTH_SHORT)
                .show();
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

    private void renderImage(String url, ImageView image){
        Picasso.with(CinemaDetailsActivity.this)
                .load(url)
                .placeholder(R.color.colorPosterBackground)
                .error(R.drawable.ic_cinema)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        mSmallPosterImageView.setBackgroundColor(ContextCompat.getColor(CinemaDetailsActivity.this , android.R.color.transparent));
                        final Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                        Palette.from(bitmap)
                                .generate(palette -> {
                                    mFABCinemaMenu.setVisibility(View.VISIBLE);
                                    Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                                    Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                                    if (mutedSwatch != null) {
                                        mTabLayout.setBackgroundColor(mutedSwatch.getRgb());
                                        mFABCinemaMenu.setBackgroundTintList(ColorStateList.valueOf(mutedSwatch.getRgb()));
                                    }
                                    if (darkMutedSwatch != null){
                                        getCollapsingToolbarLayout().setContentScrimColor(darkMutedSwatch.getRgb());
                                        getCollapsingToolbarLayout().setBackgroundColor(darkMutedSwatch.getRgb());
                                    }
                                });
                    }

                    @Override
                    public void onError() {
                        mFABCinemaMenu.setVisibility(View.VISIBLE);
                    }
                });
    }
}
