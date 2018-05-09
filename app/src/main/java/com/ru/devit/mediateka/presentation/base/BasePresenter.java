package com.ru.devit.mediateka.presentation.base;

public abstract class BasePresenter<View extends BaseView> {

    private View view;

    protected View getView(){
        return view;
    }

    public void setView(View view){
        this.view = view;
    }

    public abstract void initialize();
    public abstract void onDestroy();
}
