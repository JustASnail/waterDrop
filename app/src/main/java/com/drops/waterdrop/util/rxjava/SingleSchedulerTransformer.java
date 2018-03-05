package com.drops.waterdrop.util.rxjava;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/04 23:52
 */

public class SingleSchedulerTransformer<T> implements Single.Transformer<T, T> {
    @Override
    public Single<T> call(Single<T> tSingle) {
        return tSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
