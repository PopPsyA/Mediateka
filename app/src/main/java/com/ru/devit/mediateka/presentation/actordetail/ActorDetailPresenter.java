package com.ru.devit.mediateka.presentation.actordetail;

import com.ru.devit.mediateka.domain.actorusecases.GetActorById;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import javax.inject.Inject;

public class ActorDetailPresenter extends BasePresenter<ActorDetailPresenter.View> {

    private final GetActorById useCaseGetCinemaById;
    private int actorId;

    public ActorDetailPresenter(GetActorById useCaseGetCinemaById) {
        this.useCaseGetCinemaById = useCaseGetCinemaById;
    }

    @Override
    public void initialize() {
        getView().showLoading();
        useCaseGetCinemaById.searchActorById(actorId);
        useCaseGetCinemaById.subscribe(new ActorDetailSubscriber());
    }

    @Override
    public void onDestroy() {
        useCaseGetCinemaById.dispose();
        setView(null);
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public interface View extends BaseView{
        void showActorDetail(Actor actor);
    }

    private final class ActorDetailSubscriber extends UseCaseSubscriber<Actor>{
        @Override
        public void onNext(Actor actor) {
            getView().showActorDetail(actor);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            getView().hideLoading();
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }
    }
}
