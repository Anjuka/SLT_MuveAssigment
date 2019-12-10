package com.anjukakoralage.muveassigment.utils;

import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

/**
 * An RxJava event bus implementation.
 * Created by Ihor Havryliuk on 24.11.2018.
 */

public class RxBus {

    private final PublishRelay<Object> relay = PublishRelay.create();

    public void send( Object o ) {
        relay.accept( o );
    }

    public Observable<Object> obtain() {
        return relay;
    }
}
