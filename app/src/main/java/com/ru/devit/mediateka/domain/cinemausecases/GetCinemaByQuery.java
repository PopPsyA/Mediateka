package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class GetCinemaByQuery extends UseCase<List<Cinema>> {

    private Action actionOnNext;
    private Action actionOnDataLoaded;
    private Action actionClearAdapter;
    private boolean isDataLoaded = false;
    private final CinemaRepository repository;
    private final FlowableProcessor<String> processor = PublishProcessor.create();

    @Inject public GetCinemaByQuery(Scheduler executorThread ,
                                    Scheduler uiThread ,
                                    CinemaRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    public GetCinemaByQuery onNext(Action actionOnNext){
        this.actionOnNext = actionOnNext;
        return this;
    }

    public GetCinemaByQuery onDataLoaded(Action actionOnDataLoaded){
        this.actionOnDataLoaded = actionOnDataLoaded;
        return this;
    }

    public GetCinemaByQuery onClearAdapter(Action actionClearAdapter){
        this.actionClearAdapter = actionClearAdapter;
        return this;
    }

    public void onNextQuery(String query) {
        processor.onNext(query);
    }

    public void removeAction(){
        this.actionOnNext = null;
    }

    @Override
    protected Flowable<List<Cinema>> createUseCase() {
        return processor
                .flatMap(s -> {
                    if (!isDataLoaded){
                        return Flowable.just(s)
                                .doOnNext(s1 -> {
                                    if (s1.isEmpty()) actionOnDataLoaded.run();
                                    else actionOnNext.run();
                                });
                    } else {
                        actionOnDataLoaded.run();
                        return Flowable.just(s)
                                .doOnComplete(() -> {
                                    actionClearAdapter.run();
                                    isDataLoaded = false;
                                });
                    }
                })
                .debounce(800 , TimeUnit.MILLISECONDS)
                .filter(q -> !q.isEmpty())
                .distinctUntilChanged()
                .switchMap(s -> {
                    isDataLoaded = true;
                    return repository.searchCinemas(s);
                });
    }
}
