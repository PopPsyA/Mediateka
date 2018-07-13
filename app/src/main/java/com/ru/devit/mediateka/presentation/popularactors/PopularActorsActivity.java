package com.ru.devit.mediateka.presentation.popularactors;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.actor.ActorModule;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailActivity;
import com.ru.devit.mediateka.presentation.actorlist.ActorListAdapter;
import com.ru.devit.mediateka.presentation.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

public class PopularActorsActivity extends BaseActivity implements PopularActorsPresenter.View {

    private RecyclerView mRecyclerView;
    private ActorListAdapter adapter;
    private ProgressBar mProgressBar;

    @Inject PopularActorsPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_popular_actors;
    }

    @Override
    public void showPopularActors(List<Actor> actors) {
        adapter.addAll(actors);
    }

    @Override
    public void showOnError() {
        Snackbar.make(mRecyclerView , getString(R.string.message_unknown_error) , Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showActorDetail(int actorId , int viewHolderPos){
        Intent intent = ActorDetailActivity.makeIntent(this, actorId);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_actors);
        mProgressBar = findViewById(R.id.pb_actors_list);
        initAdapter();
    }

    @Override
    protected void initDagger() {
        MediatekaApp.getComponentsManager()
                .plusActorComponent()
                .inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initPresenter() {
        presenter.setView(this);
        presenter.initialize();
    }

    @Override
    protected void onDestroy() {
        MediatekaApp
                .getComponentsManager()
                .clearActorComponent();
        super.onDestroy();
    }

    private void initAdapter(){
        adapter = new ActorListAdapter((actorId, viewHolderPosition) -> presenter.onActorClicked(actorId , viewHolderPosition));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
