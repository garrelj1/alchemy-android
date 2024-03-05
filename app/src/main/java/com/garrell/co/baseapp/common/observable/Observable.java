package com.garrell.co.baseapp.common.observable;

public interface Observable<LISTENER_CLASS> {

    void registerListener(LISTENER_CLASS listener);

    void unregisterListener(LISTENER_CLASS listener);
}
