package com.ru.devit.mediateka.domain;

import io.reactivex.functions.Action;

public class Actions {

    private Action actionOnNext;
    private Action actionOnDataLoaded;
    private Action actionOnClearAdapter;

    public Actions(Action actionOnNext, Action actionOnDataLoaded, Action actionOnClearAdapter) {
        this.actionOnNext = actionOnNext;
        this.actionOnDataLoaded = actionOnDataLoaded;
        this.actionOnClearAdapter = actionOnClearAdapter;
    }

    public void onNext() throws Exception {
        actionOnNext.run();
    }

    public void onDataLoaded() throws Exception {
        actionOnDataLoaded.run();
    }

    public void onClearAdapter() throws Exception {
        actionOnClearAdapter.run();
    }

    public void removeActions(){
        actionOnNext = null;
        actionOnDataLoaded = null;
        actionOnClearAdapter = null;
    }
}
