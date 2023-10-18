package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable mdisposable ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Observable<Long> observable = getObSerVerBleUser();
        Observer<Long> Observer = getObserverUser();

        // dang ky lang nghe,lien ket 2 tahng lai
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // nhaanj data
                .subscribe(Observer);

    }

    private Observer<Long> getObserverUser(){
        return new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {// khi 2 thang lawng nghe nhau
                Log.e("SUB", "onSubscribe");
                mdisposable = d;
            }

            @Override
            public void onNext(@NonNull Long longNumber) { //
                Log.e("SUB", "onNext" + longNumber);

                if(longNumber == 5 ){
                    mdisposable.dispose();
                }




            }

            @Override
            public void onError(@NonNull Throwable e){
                Log.e("SUB","onError");
        }

            @Override
            public void onComplete() {
            Log.e("SUB", "onComplete");
                Log.e("SUB","observer complete" + Thread.currentThread().getName());

            }
        };
    }
    private Observable<Long> getObSerVerBleUser(){


        return  Observable.interval(3, 5, TimeUnit.SECONDS);



    }
    private List<User> getListUser(){
        List<User> list = new ArrayList<>();
        list.add(new User(1,"a"));
        list.add(new User(2,"b"));
        list.add(new User(3,"c"));
        list.add(new User(4,"d"));
        list.add(new User(5,"e"));
        list.add(new User(6,"f"));
        list.add(new User(7,"g"));
        return list;
    }
    protected  void onDestroy(){
        super.onDestroy();
        if(mdisposable != null){
            mdisposable.dispose();
        }
    }
}