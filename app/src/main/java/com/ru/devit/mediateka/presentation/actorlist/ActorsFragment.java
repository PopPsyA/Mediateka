package com.ru.devit.mediateka.presentation.actorlist;

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
import com.ru.devit.mediateka.di.actor.ActorListModule;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailActivity;
import com.ru.devit.mediateka.presentation.common.OnActorClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ActorsFragment extends Fragment implements ActorsPresenter.View {

    private static final String ACTORS = "cinema_actors";

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ActorListAdapter adapter;

    @Inject ActorsPresenter presenter;

    public static ActorsFragment newInstance(List<Actor> actors){
        ActorsFragment fragment = new ActorsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ACTORS , (ArrayList<? extends Parcelable>) actors);
        fragment.setArguments(args);
        return fragment;
    }

    public static ActorsFragment newInstance() {
        return new ActorsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actors , container , false);
        initDagger();
        initViews(view);
        initAdapter();
        initPresenter();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args == null){
            return;
        }
        presenter.setActors(getArguments().getParcelableArrayList(ACTORS));
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
    public void clearAdapter(){
        adapter.clear();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void openActor(int actorId , int viewHolderPosition) {
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity() ,
                mRecyclerView
                        .findViewHolderForAdapterPosition(viewHolderPosition)
                        .itemView
                        .findViewById(R.id.iv_actor_avatar) ,
                getString(R.string.transition_actor_avatar));
        Intent intent = ActorDetailActivity.makeIntent(getContext(), actorId);
        ActivityCompat.startActivity(getContext() , intent , activityOptions.toBundle());
    }

    @Override
    public void showActors(List<Actor> actors) {
        adapter.addAll(actors);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        MediatekaApp.getComponentsManager().clearActorComponent();
        super.onDestroy();
    }

    public void textFromSearchField(String query) {
        presenter.onGetTextFromSearchField(query);
    }

    private void initDagger() {
        MediatekaApp.getComponentsManager()
                .plusActorComponent()
                .plusActorListComponent(new ActorListModule())
                .inject(this);
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_actors);
        mProgressBar = view.findViewById(R.id.pb_actors_list);
    }

    private void initPresenter() {
        presenter.setView(this);
        presenter.initialize();
    }

    private void initAdapter() {
        adapter = new ActorListAdapter((actorId, viewHolderPosition) -> presenter.onActorClicked(actorId , viewHolderPosition));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
