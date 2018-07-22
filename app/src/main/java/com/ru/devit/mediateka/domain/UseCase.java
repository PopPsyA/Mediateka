package com.ru.devit.mediateka.domain;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class UseCase<T> {

    protected int pageIndex = 1;

    private final Scheduler executorThread;
    private final Scheduler uiThread;
    private final CompositeDisposable compositeDisposable;

    public UseCase(Scheduler executorThread,
                   Scheduler uiThread) {
        this.executorThread = executorThread;
        this.uiThread = uiThread;
        compositeDisposable = new CompositeDisposable();
    }

    public void subscribe(DisposableSubscriber<T> disposableSubscriber){
        if (disposableSubscriber == null){
            throw new IllegalArgumentException("subscriber must not be null");
        }
        Flowable<T> flowable = createUseCase()
                .subscribeOn(executorThread)
                .observeOn(uiThread);

        DisposableSubscriber<T> subscriber = flowable.subscribeWith(disposableSubscriber);
        compositeDisposable.add(subscriber);
    }

    public void setCurrentPage(int pageIndex){
        this.pageIndex = pageIndex;
    }

    public void dispose(){
        if (!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    protected abstract Flowable<T> createUseCase();
}
