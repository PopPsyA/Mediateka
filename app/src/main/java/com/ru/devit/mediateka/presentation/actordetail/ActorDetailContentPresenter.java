package com.ru.devit.mediateka.presentation.actordetail;

import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

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

    public interface View extends BaseView{
        void showActorInfo(Actor actor);
    }
}
