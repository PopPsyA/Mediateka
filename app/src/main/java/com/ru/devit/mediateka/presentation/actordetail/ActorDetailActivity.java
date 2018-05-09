package com.ru.devit.mediateka.presentation.actordetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.actor.ActorDetailModule;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.common.ViewPagerAdapter;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemasFragment;
import com.ru.devit.mediateka.utils.AnimUtils;
import com.ru.devit.mediateka.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActorDetailActivity extends BaseActivity implements ActorDetailPresenter.View {

    private static final String ACTOR_ID = "actor_id";

    private CircleImageView mActorAvatar;
    private TextView mTextViewActorName;
    private ImageView mImageViewActorBackground;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ProgressBar mProgressBarBackGround;
    private ProgressBar mProgressBarActor;
    private AppBarLayout mAppBar;

    @Inject ActorDetailPresenter presenter;

    public static Intent makeIntent(Context context , int actorID){
        Intent intent = new Intent(context , ActorDetailActivity.class);
        intent.putExtra(ACTOR_ID , actorID);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_actor_detail;
    }

    @Override
    public void showActorDetail(Actor actor) {
        AnimUtils.startRevealAnimation(mImageViewActorBackground);
        renderImage(actor.getProfilePath() , mActorAvatar , Constants.IMG_PATH_W185 , true);
        renderImage(actor.getProfileBackgroundPath() , mImageViewActorBackground , Constants.IMG_PATH_W500 , false);
        mTextViewActorName.setText(actor.getName());
        addOffsetChangeListener(mAppBar , actor.getName());
        setUpViewPager(mViewPager , mTabLayout , actor);
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
        if (isAboveLollipop()){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initViews(){
        mActorAvatar = findViewById(R.id.actor_detail_avatar);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mTextViewActorName = findViewById(R.id.actor_detail_name);
        mImageViewActorBackground = findViewById(R.id.actor_detail_background);
        mProgressBarBackGround = findViewById(R.id.pb_background_actor_poster);
        mProgressBarActor = findViewById(R.id.pb_actor);
        mAppBar = findViewById(R.id.app_bar_actor);
    }

    @Override
    protected void initDagger(){
        MediatekaApp.getComponentsManager()
                .plusActorComponent()
                .plusActorDetailComponent(new ActorDetailModule())
                .inject(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initPresenter(){
        presenter.setView(this);
        presenter.setActorId(getIntent().getExtras().getInt(ACTOR_ID));
        presenter.initialize();
    }

    @Override
    public void showLoading() {
        mProgressBarActor.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBarActor.setVisibility(View.GONE);
    }

    private void setUpViewPager(ViewPager viewPager , TabLayout tabLayout , Actor actor){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ActorDetailContentFragment.newInstance(actor) , getString(R.string.message_info));
        adapter.addFragment(SmallCinemasFragment.newInstance(actor.getCinemas()) , getString(R.string.message_cinemas));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void renderImage(final String url , final ImageView image , final String size , boolean itAvatar){
        Picasso.with(ActorDetailActivity.this)
                .load(size + url)
                .placeholder(itAvatar ? R.color.colorDarkBackground : R.color.colorWhite)
                .error(R.drawable.ic_actor_default_avatar)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (itAvatar){
                            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                            Palette.from(bitmap)
                                    .generate(palette -> {
                                        Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                                        Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                                        if (darkMutedSwatch != null) {
                                            getCollapsingToolbarLayout().setBackgroundColor(darkMutedSwatch.getRgb());
                                            getCollapsingToolbarLayout().setContentScrimColor(darkMutedSwatch.getRgb());
                                        }
                                        if (mutedSwatch != null) {
                                            mTabLayout.setBackgroundColor(mutedSwatch.getRgb());
                                        }
                                    });
                        } else {
                            mProgressBarBackGround.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        if (mActorAvatar.getDrawable() == null){
                            mActorAvatar.setImageDrawable(VectorDrawableCompat
                                    .create(getResources() , R.drawable.ic_actor_default_avatar , getTheme()));
                        }
                        mProgressBarBackGround.setVisibility(View.GONE);
                    }
                });
    }
}
