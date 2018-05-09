package com.ru.devit.mediateka.presentation.actorlist;

import com.ru.devit.mediateka.domain.actorusecases.GetActorsByQuery;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import java.util.List;

import javax.inject.Inject;

public class ActorsPresenter extends BasePresenter<ActorsPresenter.View> {

    private List<Actor> actors;
    private final GetActorsByQuery getActorsByQuery;

    public ActorsPresenter(GetActorsByQuery getActorsByQuery) {
        this.getActorsByQuery = getActorsByQuery;
    }

    @Override
    public void initialize() {
        getView().showLoading();
        getActorsByQuery.subscribe(new ActorsSubscriber());
    }

    public void setActors(List<Actor> actors){
        this.actors = actors;
        showActors(this.actors);
    }

    public void onGetTextFromSearchField(String query) {
        getActorsByQuery.setQuery(query);
    }

    private void showActors(List<Actor> actors){
        getView().showActors(actors);
        getView().hideLoading();
    }

    public void onActorClicked(int actorId , int viewHolderPosition){
        getView().openActor(actorId , viewHolderPosition);
    }

    public void onDestroy() {
        getActorsByQuery.dispose();
        setView(null);
    }

    final class ActorsSubscriber extends UseCaseSubscriber<List<Actor>>{
        @Override
        public void onNext(List<Actor> actors) {
            getView().showActors(actors);
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
            getView().hideLoading();
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }
    }

    public interface View extends BaseView{
        void openActor(int actorId , int viewHolderPosition);
        void showActors(List<Actor> actors);
    }
}
