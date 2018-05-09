package com.ru.devit.mediateka.presentation.actordetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.actor.ActorDetailModule;
import com.ru.devit.mediateka.models.model.Actor;

import javax.inject.Inject;

public class ActorDetailContentFragment extends Fragment implements ActorDetailContentPresenter.View {

    private static final String ACTOR = "actor_detail";

    private TextView mTextViewBiography;
    private TextView mTextViewDateOfBorn;
    private TextView mTextViewAge;
    private TextView mTextViewBirthplace;

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
        presenter.setActor(getArguments().getParcelable(ACTOR));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showActorInfo(Actor actor) {
        mTextViewBiography.setText(actor.getBiography().isEmpty() ?
                getString(R.string.message_biography_not_entered) : actor.getBiography());
        mTextViewDateOfBorn.setText(actor.getBirthDay());
        mTextViewAge.setText(String.format(getString(R.string.years) , actor.getAge()));
        mTextViewBirthplace.setText(actor.getPlaceOfBirth());
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
        Log.d("bdsm" , "onDestroy ActorDetailContentFragment");
        super.onDestroy();
    }

    private void initViews(View view){
        mTextViewBiography = view.findViewById(R.id.tv_actor_detail_biography);
        mTextViewDateOfBorn = view.findViewById(R.id.tv_actor_detail_date_of_born);
        mTextViewAge = view.findViewById(R.id.tv_actor_detail_age);
        mTextViewBirthplace = view.findViewById(R.id.tv_actor_detail_birthplace);
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
