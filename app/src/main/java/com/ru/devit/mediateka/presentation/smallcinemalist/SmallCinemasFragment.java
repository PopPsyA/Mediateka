package com.ru.devit.mediateka.presentation.smallcinemalist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.cinemadetail.CinemaDetailModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SmallCinemasFragment extends Fragment implements SmallCinemasPresenter.View {

    private static final String CINEMAS = "actor_cinemas";
    private static final String WITH_OUT_HEADER = "with_out_header";

    private RecyclerView mRecyclerViewCinemas;
    private SmallCinemaListAdapter adapter;
    private ProgressBar mProgressBar;

    @Inject SmallCinemasPresenter presenter;

    public static SmallCinemasFragment newInstance(List<Cinema> cinemas){
        SmallCinemasFragment fragment = new SmallCinemasFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CINEMAS , (ArrayList<? extends Parcelable>) cinemas);
        fragment.setArguments(args);
        return fragment;
    }

    public static SmallCinemasFragment newInstance(boolean withOutHeader) {
        SmallCinemasFragment fragment = new SmallCinemasFragment();
        Bundle args = new Bundle();
        args.putBoolean(WITH_OUT_HEADER, withOutHeader);
        fragment.setArguments(args);
        return fragment;
    }

    public static SmallCinemasFragment newInstance() {
        return newInstance(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_small_cinemas, container , false);
        initViews(view);
        initDagger();
        initPresenter();
        initAdapter();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null){
            if (args.getBoolean(WITH_OUT_HEADER)){ // if we in search activity
                return;
            }
            presenter.setCinemas(getArguments().getParcelableArrayList(CINEMAS));
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void openCinema(int cinemaId , int viewHolderPosition) {
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity() ,
                mRecyclerViewCinemas
                        .findViewHolderForAdapterPosition(viewHolderPosition)
                        .itemView
                        .findViewById(R.id.iv_actor_detail_cinema_poster) ,
                getString(R.string.transition_cinema_poster_image)
        );
        Intent intent = CinemaDetailsActivity.makeIntent(getContext(), cinemaId);
        ActivityCompat.startActivity(getActivity() , intent , activityOptions.toBundle());
    }

    @Override
    public void clearAdapter(){
        adapter.clear();
    }

    @Override
    public void showCinemas(List<Cinema> cinemas) {
        adapter.addAll(cinemas);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        MediatekaApp.getComponentsManager().clearCinemaComponent();
        super.onDestroy();
    }

    public void textFromSearchField(String query) {
        presenter.onGetTextFromSearchField(query);
    }

    private void initDagger() {
        MediatekaApp.getComponentsManager()
                .plusCinemaComponent()
                .plusCinemaDetailComponent(new CinemaDetailModule())
                .inject(this);
    }

    private void initViews(View view) {
        mRecyclerViewCinemas = view.findViewById(R.id.rv_actor_detail_cinemas);
        mProgressBar = view.findViewById(R.id.pb_small_cinemas);
    }

    private void initPresenter(){
        presenter.setView(this);
        presenter.initialize();
    }

    private void initAdapter(){
        if (getArguments() != null){
            adapter = new SmallCinemaListAdapter(getContext() ,
                    (cinemaId, viewHolderPosition) -> presenter.onCinemaClicked(cinemaId , viewHolderPosition) ,
                    getArguments().getBoolean(WITH_OUT_HEADER) ,
                    true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerViewCinemas.setLayoutManager(linearLayoutManager);
            mRecyclerViewCinemas.setAdapter(adapter);
        }
    }
}
