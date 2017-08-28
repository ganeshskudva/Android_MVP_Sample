package com.example.gkudva.doordash.presenter;

/**
 * Created by gkudva on 25/08/17.
 */

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
