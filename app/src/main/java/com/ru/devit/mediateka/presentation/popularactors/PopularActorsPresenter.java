package com.ru.devit.mediateka.presentation.popularactors;

import com.ru.devit.mediateka.domain.actorusecases.GetActors;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class PopularActorsPresenter extends BasePresenter<PopularActorsPresenter.View> {

    private final GetActors useCaseGetActors;

    @Inject public PopularActorsPresenter(GetActors useCaseGetActors) {
        this.useCaseGetActors = useCaseGetActors;
    }

    @Override
    public void initialize() {
        getView().showLoading();
        useCaseGetActors.subscribe(new DisposableSubscriber<List<Actor>>() {
            @Override
            public void onNext(List<Actor> actors) {
                getView().showPopularActors(actors);
            }

            @Override
            public void onError(Throwable t) {
                getView().showOnError();
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void onDestroy() {
        useCaseGetActors.dispose();
    }

    public void onActorClicked(int actorId, int viewHolderPosition) {
        getView().showActorDetail(actorId , viewHolderPosition);
    }

    public interface View extends BaseView {
        void showPopularActors(List<Actor> actors);
        void showOnError();
        void showActorDetail(int actorId , int viewHolderPos);
    }
}

