package com.bytetradei.pro.rxlearning;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.internal.operators.single.SingleFlatMapIterableObservable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends Activity {

    Context mContext;
    final String MLOGTAG = "MLOGTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

    }

    public void rxlearning27(View v) {

        Flowable.just(1, 2, 3, 4, 5).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning26(View v) {

        //只有结果
        Completable.timer(5, TimeUnit.SECONDS).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(MLOGTAG, "on sub ->" + d.isDisposed());
            }

            @Override
            public void onComplete() {
                Log.v(MLOGTAG, "on complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.v(MLOGTAG, "on error");
            }
        });

    }

    public void rxlearning25(View v) {

        //最后一个消息会被缓存，并通知给下一个订阅者
        BehaviorSubject<Integer> mBehaviorSubject = BehaviorSubject.create();
        mBehaviorSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept => " + integer);
            }
        });

        mBehaviorSubject.onNext(1);
        mBehaviorSubject.onNext(2);
        mBehaviorSubject.onNext(3);
        mBehaviorSubject.onNext(4);
        mBehaviorSubject.onNext(5);


        mBehaviorSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning24(View v) {

        //调用onComplete之前不收到任何消息，调用onComplete只收到最后一个
        AsyncSubject<Integer> mAsyncSubject = AsyncSubject.create();

        mAsyncSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

        mAsyncSubject.onNext(1);
        mAsyncSubject.onNext(2);
        mAsyncSubject.onNext(3);
        mAsyncSubject.onNext(4);
        mAsyncSubject.onNext(5);

        mAsyncSubject.onComplete();

    }

    public void rxlearning23(View v) {

        //通知每个订阅者
        PublishSubject<Integer> mPublishSubject = PublishSubject.create();

        mPublishSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept 1 ->" + integer);
            }
        });

        mPublishSubject.onNext(1);
        mPublishSubject.onNext(2);
        mPublishSubject.onNext(3);

        mPublishSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept 2 ->" + integer);
            }
        });

        mPublishSubject.onNext(4);
        mPublishSubject.onNext(5);
        mPublishSubject.onNext(6);

    }

    public void rxlearning22(View v) {

        Observable.interval(1, TimeUnit.SECONDS)
                .take(15)
                .window(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(Observable<Long> longObservable) throws Exception {

                        longObservable.subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.v(MLOGTAG, "accept ->" + aLong);
                            }
                        });

                    }
                });
    }

    public void rxlearning21(View v) {

        //累加，打印每步结果
        Observable.just(1, 2, 3).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning20(View v) {

        //累加
        Observable.just(1, 2, 3, 4).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning19(View v) {

        //聚合
        Observable.merge(Observable.just(1, 3, 5), Observable.just(2, 4, 6), Observable.just(7, 8, 9)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning18(View v) {

        //取最后一个值，没有返回默认值
        Observable.just(1, 2, 3, 4, 5).last(4).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning17(View v) {

        Observable<Integer> mObeserve = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() {
                return Observable.just(1, 2, 3);
            }
        });

        mObeserve.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(MLOGTAG, "on subscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.v(MLOGTAG, "on next ->" + integer);
                Log.v(MLOGTAG, "on next thread id   ->" + Thread.currentThread().getId());
                Log.v(MLOGTAG, "on next thread name ->" + Thread.currentThread().getState().name());
            }

            @Override
            public void onError(Throwable e) {
                Log.v(MLOGTAG, "on error ->" + e.toString().trim());
            }

            @Override
            public void onComplete() {
                Log.v(MLOGTAG, "on complete");
            }
        });

    }

    public void rxlearning16(View v) {

        //过滤
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                if (!emitter.isDisposed()) {

                    emitter.onNext(1);
                    Thread.sleep(300l);
                    emitter.onNext(2);
                    Thread.sleep(400l);
                    emitter.onNext(3);
                    Thread.sleep(500l);
                    emitter.onComplete();

                }

            }
        }).debounce(400, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning15(View v) {

        Observable.just(1, 2, 3, 4, 5).buffer(3, 2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integers.size());
                for (Integer mInteger : integers) {
                    Log.v(MLOGTAG, "minteger ->" + mInteger);
                }
            }
        });

    }

    public void rxlearning14(View v) {

        //去重
        Observable.just(1, 2, 3, 4, 4, 3, 2, 1).distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning13(View v) {

        //连接两个消息池
        Observable.concat(Observable.fromArray(1, 2, 3, 4), Observable.fromArray(3, 4, 5, 6, 7, 8)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning12(View v) {

        Single.just(new Random(1).nextInt()).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(MLOGTAG, "disposable ->" + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.v(MLOGTAG, "on success ->" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.v(MLOGTAG, "on error ->" + e.toString());
            }
        });
    }

    public void rxlearning11(View v) {

        Observable.interval(3, 2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {

            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                Log.v(MLOGTAG, "on subscribe");
            }

            @Override
            public void onNext(Long aLong) {
                Log.v(MLOGTAG, "on next ->" + aLong);
                if (aLong == 10) {
                    if (!mDisposable.isDisposed()) {
                        mDisposable.dispose();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.v(MLOGTAG, "on error ->" + e.toString().trim());
            }

            @Override
            public void onComplete() {
                Log.v(MLOGTAG, "on complete");
            }
        });

    }

    public void rxlearning10(View v) {

        //延时操作
        Observable.timer(5, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.v(MLOGTAG, "accpet ->" + aLong);
            }
        });

    }

    public void rxlearning9(View v) {

        //最多接收的数据量
        Observable.just(1, 2, 3, 4, 5).take(4).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(MLOGTAG, "on subscribe ->" + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                Log.v(MLOGTAG, "on next ->" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.v(MLOGTAG, "on error ->" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.v(MLOGTAG, "on complete");
            }
        });

    }

    public void rxlearning8(View v) {

        //跳过多少条数据
        Observable.just(1, 2, 3, 4, 5).skip(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning7(View v) {

        //筛选
        Observable.just(1, 2, 3, 4, 5).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer > 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "accept ->" + integer);
            }
        });

    }

    public void rxlearning6(View v) {

        //接收前做操作
        Observable.just(1, 2, 3, 4, 5).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "doOnNext accept ->" + integer);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.v(MLOGTAG, "subsribe ->" + integer);
            }
        });

    }

    public void rxlearning5(View v) {

        //获取消息，处理后发出，连续的
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {

                if (!emitter.isDisposed()) {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                }

            }

        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {

                List<String> mDataList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    if (integer == 1) {
                        mDataList.add("hello ->" + i);
                    } else if (integer == 2) {
                        mDataList.add("world ->" + i);
                    } else if (integer == 3) {
                        mDataList.add("demo ->" + i);
                    }
                }
                return Observable.fromIterable(mDataList).delay(1000, TimeUnit.MILLISECONDS);

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.v(MLOGTAG, "accept ->" + s);

            }
        });

    }

    public void rxlearning4(View v) {

        //获取消息，处理后发出，非连续的
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(1);
                    emitter.onNext(2);
                }
            }

        }).flatMap(new Function<Integer, ObservableSource<String>>() {

            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {

                List<String> mDataList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    if (integer == 1) {
                        mDataList.add("hello ->" + i);
                    } else if (integer == 2) {
                        mDataList.add("world ->" + i);
                    }
                }
                return Observable.fromIterable(mDataList).delay(1000, TimeUnit.MILLISECONDS);

            }

        }).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) {

                Log.v(MLOGTAG, "accept ->" + o.toString().trim());

            }

        });

    }

    public void rxlearning3(View v) {

        //获取消息，改变消息
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {

                if (!emitter.isDisposed()) {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                }

            }
        }).map(new Function<Integer, Object>() {

            @Override
            public Object apply(Integer integer) {
                return "change ->" + integer;
            }

        }).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) {

                Log.v(MLOGTAG, "accept ->" + o.toString().trim());

            }
        });

    }

    public void rxlearning1(View v) {

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {

                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);

            }

        }).subscribe(new Observer<Integer>() {

            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                Log.v("on subscribe", d.toString());
            }

            @Override
            public void onNext(Integer integer) {
                Log.v("on next", "" + integer);
                if (integer == 3) {
                    if (mDisposable != null) {
                        mDisposable.dispose();
                    } else {
                        Log.v("on next", "mDisposable == null");
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.v("on error", "" + e.toString().trim());
            }

            @Override
            public void onComplete() {
                Log.v("on complete", "...");
            }

        });

    }

    public void rxlearning2(View v) {

        //混合两个消息，取较短的长度
        Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, Object>() {

            @Override
            public Object apply(String s, Integer integer) {
                return s + integer;
            }

        }).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) {
                Log.v(MLOGTAG, "accept => " + o.toString().trim());
            }
        });

    }

    private Observable<String> getStringObservable() {

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                if (!emitter.isDisposed()) {

                    emitter.onNext("hello");
                    emitter.onNext("world");
                    emitter.onNext("baby");

                }

            }
        });

    }

    private Observable<Integer> getIntegerObservable() {

        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                if (!emitter.isDisposed()) {

                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                    emitter.onNext(4);
                    emitter.onNext(5);

                }

            }
        });

    }

}
