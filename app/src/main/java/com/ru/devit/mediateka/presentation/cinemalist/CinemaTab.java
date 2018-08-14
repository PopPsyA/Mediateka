package com.ru.devit.mediateka.presentation.cinemalist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.common.CinemaTabSelectorView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public enum CinemaTab {

    POPULAR {
        @Override
        public <T extends CinemaTabSelectorView> void loadCinemaList(UseCase<List<Cinema>> useCase,
                                                                     UseCaseSubscriber<List<Cinema>> subscriber,
                                                                     @NonNull T view) {
            super.loadCinemaList(useCase , subscriber , view);
            view.onPopularTabSelected();
        }
    },
    TOP_RATED {
        @Override
        public <T extends CinemaTabSelectorView> void loadCinemaList(UseCase<List<Cinema>> useCase,
                                                                     UseCaseSubscriber<List<Cinema>> subscriber,
                                                                     @NonNull T view) {
            super.loadCinemaList(useCase , subscriber , view);
            view.onTopRatedTabSelected();
        }
    },
    UP_COMING {
        @Override
        public <T extends CinemaTabSelectorView> void loadCinemaList(UseCase<List<Cinema>> useCase,
                                                                     UseCaseSubscriber<List<Cinema>> subscriber,
                                                                     @NonNull T view) {
            super.loadCinemaList(useCase , subscriber , view);
            view.onUpComingTabSelected();
        }
    };

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void dispose(){
        if (!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    public <T extends CinemaTabSelectorView> void loadCinemaList(@Nullable UseCase<List<Cinema>> useCase,
                                                                 @Nullable UseCaseSubscriber<List<Cinema>> subscriber,
                                                                 @NonNull T view){
        if (useCase != null && subscriber != null){
            useCase.subscribe(subscriber);
            compositeDisposable.add(subscriber);
        }
    }
}
