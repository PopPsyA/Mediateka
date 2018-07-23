package com.ru.devit.mediateka.presentation.actordetail;

import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import java.util.List;

public class ActorDetailContentPresenter extends BasePresenter<ActorDetailContentPresenter.View> {

    private Actor actor;

    public ActorDetailContentPresenter() {}

    @Override
    public void initialize() {
        getView().showLoading();
    }

    @Override
    public void onDestroy() {
        setView(null);
    }

    public void setActor(Actor actor){
        this.actor = actor;
        getView().showActorInfo(this.actor);
    }

    public void onPhotoClicked(int position, int viewHolderPos) {
        getView().showDetailedPhoto(position , viewHolderPos , actor.getPostersUrl());
    }

    public interface View extends BaseView{
        void showActorInfo(Actor actor);
        void showDetailedPhoto(int position , int viewHolderPos , List<String> posterUrls);
    }
}
