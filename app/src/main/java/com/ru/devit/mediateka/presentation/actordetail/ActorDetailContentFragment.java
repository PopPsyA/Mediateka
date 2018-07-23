package com.ru.devit.mediateka.presentation.actordetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.actor.ActorDetailModule;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.posterslider.PosterSliderActivity;

import java.util.List;

import javax.inject.Inject;

public class ActorDetailContentFragment extends Fragment implements ActorDetailContentPresenter.View {

    private static final String ACTOR = "actor_detail";

    private TextView mTextViewBiography;
    private TextView mTextViewDateOfBorn;
    private TextView mTextViewAge;
    private TextView mTextViewBirthplace;
    private RecyclerView mRecyclerViewPhotos;
    private ActorDetailPhotoAdapter mAdapter;

    @Inject ActorDetailContentPresenter presenter;

    public static ActorDetailContentFragment newInstance(Actor actor){
        ActorDetailContentFragment fragment = new ActorDetailContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ACTOR , actor);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actor_detail_content , container , false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initDagger();
        initPresenter();
        if (getArguments() != null){
            presenter.setActor(getArguments().getParcelable(ACTOR));
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showActorInfo(Actor actor) {
        mTextViewBiography.setText(actor.getBiography().isEmpty() ?
                getString(R.string.message_biography_not_entered) : actor.getBiography());
        mTextViewDateOfBorn.setText(actor.getBirthDay());
        mTextViewAge.setText(String.format(getString(R.string.years) , actor.getAge()));
        mTextViewBirthplace.setText(actor.getPlaceOfBirth());
        mAdapter.addAll(actor.getPostersUrl());
    }

    @Override
    public void showDetailedPhoto(int position , int viewHolderPos , List<String> posterUrls){
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity() ,
                mRecyclerViewPhotos
                        .findViewHolderForAdapterPosition(viewHolderPos)
                        .itemView
                        .findViewById(R.id.iv_actor_photo) ,
                getString(R.string.transition_actor_avatar)
        );
        Intent intent = PosterSliderActivity.makeIntent(requireActivity() ,
                posterUrls ,
                getString(R.string.transition_actor_avatar) ,
                position);
        ActivityCompat.startActivity(requireActivity() , intent , activityOptions.toBundle());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        MediatekaApp.getComponentsManager().clearActorComponent();
        super.onDestroy();
    }

    private void initViews(View view){
        mTextViewBiography = view.findViewById(R.id.tv_actor_detail_biography);
        mTextViewDateOfBorn = view.findViewById(R.id.tv_actor_detail_date_of_born);
        mTextViewAge = view.findViewById(R.id.tv_actor_detail_age);
        mTextViewBirthplace = view.findViewById(R.id.tv_actor_detail_birthplace);
        mRecyclerViewPhotos = view.findViewById(R.id.rv_actor_detail_photos);
        mAdapter = new ActorDetailPhotoAdapter((position , viewHolderPos) -> presenter.onPhotoClicked(position , viewHolderPos));
        mRecyclerViewPhotos.setLayoutManager(new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL));
        mRecyclerViewPhotos.setAdapter(mAdapter);
    }
    private void initDagger(){
        MediatekaApp.getComponentsManager()
                .plusActorComponent()
                .plusActorDetailComponent(new ActorDetailModule())
                .inject(this);
    }
    private void initPresenter(){
        presenter.setView(this);
        presenter.initialize();
    }
}
