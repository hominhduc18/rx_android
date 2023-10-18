package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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


        Observable<User> observable = getObSerVerBleUser();
        Observer<User> Observer = getObserverUser();

        // dang ky lang nghe,lien ket 2 tahng lai
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // nhaanj data
                .subscribe(Observer);

    }

    private Observer<User> getObserverUser(){
        return new Observer<User>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {// khi 2 thang lawng nghe nhau
                Log.e("SUB", "onSubscribe");
                mdisposable = d;
            }

            @Override
            public void onNext(@NonNull User user) { //
                Log.e("SUB", "onNext" + user.toString());

                Log.e("SUB","observer Next" + Thread.currentThread().getName());

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
    private Observable<User> getObSerVerBleUser(){
//        List<User> listenUser = getListUser();
        // cachs taoj observerble wwith create
//        return Observable.create(new ObservableOnSubscribe<User>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Throwable {
//                Log.e("SUB","observable" + Thread.currentThread().getName());
//                if(listenUser == null || listenUser.isEmpty()){
//                    if(emitter.isDisposed()){
//                        emitter.onError(new Exception());
//                    }
//                }
//                for(User user: listenUser){
//                        if(!emitter.isDisposed()){// chuwa bi huy connect
//                            emitter.onNext(user);  // phat di dua lieu
//
//                        }
//                    }
//                    if(!emitter.isDisposed()){
//                        emitter.onComplete();
//                    }
//            }
//        });
        User user1 = new User(1, "a");
        User user2 = new User(2, "a");
        User[] usersArray = new User[]{user1, user2};
        return  Observable.fromArray(usersArray);

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