package com.ru.devit.mediateka.domain;


import io.reactivex.subscribers.DisposableSubscriber;

public abstract class UseCaseSubscriber<T> extends DisposableSubscriber<T> {
    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
