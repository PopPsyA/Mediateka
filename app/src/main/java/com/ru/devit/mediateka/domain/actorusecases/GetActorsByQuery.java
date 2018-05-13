package com.ru.devit.mediateka.domain.actorusecases;

import com.ru.devit.mediateka.domain.Actions;
import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Actor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class GetActorsByQuery extends UseCase<List<Actor>> {

    private Actions actions;
    private boolean isDataLoaded = false;
    private final ActorRepository repository;
    private final FlowableProcessor<String> processor = PublishProcessor.create();

    public GetActorsByQuery(Scheduler executorThread ,
                            Scheduler uiThread ,
                            ActorRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    public void setQuery(String query) {
        processor.onNext(query);
    }

    public void setActions(Actions actions){
        this.actions = actions;
    }

    public void removeActions(){
        actions.removeActions();
    }

    @Override
    protected Flowable<List<Actor>> createUseCase() {
        return processor
                .flatMap(s -> {
                    if (!isDataLoaded){
                        return Flowable.just(s)
                                .doOnNext(s1 -> {
                                    if (s1.isEmpty()) actions.onDataLoaded();
                                    else actions.onNext();
                                });
                    } else {
                        actions.onDataLoaded();
                        return Flowable.just(s)
                                .doOnComplete(() -> {
                                    actions.onClearAdapter();
                                    isDataLoaded = false;
                                });
                    }
                })
                .debounce(800 , TimeUnit.MILLISECONDS)
                .filter(query -> !query.isEmpty())
                .distinctUntilChanged()
                .switchMap(s -> {
                    isDataLoaded = true;
                    return repository.searchActors(s);
                });
    }
}
