package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.domain.Actions;
import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.utils.FormatterUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

import static com.ru.devit.mediateka.utils.FormatterUtils.DEFAULT_VALUE;

public class GetCinemaByQuery extends UseCase<List<Cinema>> {

    private Actions actions;
    private boolean isDataLoaded = false;
    private final CinemaRepository repository;
    private final FlowableProcessor<String> processor = PublishProcessor.create();

    @Inject public GetCinemaByQuery(Scheduler executorThread ,
                                    Scheduler uiThread ,
                                    CinemaRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    public void onNextQuery(String query) {
        processor.onNext(query);
    }

    public void setActions(Actions actions){
        this.actions = actions;
    }

    public void removeActions() {
        actions.removeActions();
    }

    public void sortByDate(List<Cinema> cinemas){
        Collections.sort(cinemas, (cinema, cinema2) -> {
            if (cinema.getReleaseDate().equals(FormatterUtils.DEFAULT_VALUE) || cinema2.getReleaseDate().equals(DEFAULT_VALUE)){
                return cinema.getReleaseDate().compareTo(cinema2.getReleaseDate());
            }
            int releaseDate1 = Integer.valueOf(cinema.getReleaseDate());
            int releaseDate2 = Integer.valueOf(cinema2.getReleaseDate());
            return Integer.compare(releaseDate2 , releaseDate1);
        });
    }

    @Override
    protected Flowable<List<Cinema>> createUseCase() {
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
                .filter(q -> !q.isEmpty())
                .distinctUntilChanged()
                .switchMap(s -> {
                    isDataLoaded = true;
                    return repository.searchCinemas(s);
                });
    }
}
