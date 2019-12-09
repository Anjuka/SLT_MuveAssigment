package com.anjukakoralage.muveassigment.ui.main;

/**
 * Created by  on 09,December,2019
 */
public class MainPrecenter implements MainContract.presenter {

    MainContract.view view;

    public MainPrecenter(MainContract.view view) {
        this.view = view;
    }
}
