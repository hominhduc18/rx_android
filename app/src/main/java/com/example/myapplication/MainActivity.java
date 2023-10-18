package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
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


        Observable<Serializable> observable = getObSerVerBleUser();
        Observer<Serializable> Observer = getObserverUser();

        // dang ky lang nghe,lien ket 2 tahng lai
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // nhaanj data
                .subscribe(Observer);

    }

    private Observer<Serializable> getObserverUser(){
        return new Observer<Serializable>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {// khi 2 thang lawng nghe nhau
                Log.e("SUB", "onSubscribe");
                mdisposable = d;
            }

            @Override
            public void onNext(@NonNull Serializable serializable) { //
                Log.e("SUB", "onNext" + serializable.toString());

                Log.e("SUB","observer Next" + Thread.currentThread().getName());
// so sanh kieu dua lieu minh nhan duoc

                // + nếu kiểu đưa vào là một array
                if(serializable instanceof User[]){
                    User[] users = (User[]) serializable;

                    for(User user: users ){
                        Log.e("SUB", "User infor" + serializable.toString());
                    }
                }
                // + nếu kiểu đưa vào là một String
                else if (serializable instanceof  String) {
                    String mystr = (String) serializable;
                    Log.e("SUB", "User infor " + mystr);

                }
                // + nếu kiểu đưa vào là một object
                else if (serializable instanceof User) {
                    User user = (User) serializable;
                    Log.e("SUB", "User infor" + user.toString());

                } else if (serializable instanceof List) {
                    List<User> list  = (List<User>) serializable;
                    for(User user: list){
                        Log.e("SUB", "User infor  " + user.toString());
                    }

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
    private Observable getObSerVerBleUser(){
        // đối với kiểu data Là một List thì bỏ khai báo Observable<serializable> trên pt
        // còn ở các chổi gọi có thể để hoặc cũng có thể xóa đi cũng đc ko ảnh hưởng
        List<User> listenUser = getListUser();
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
        // xuwr lys 3 data khacs nhau
        User user1 = new User(1, "a");
        User user2 = new User(2, "a");
        // trường hợp truyền vào một object thì bên model phải implement Serializable
        User user4 = new User(4, "b");

        String strDta = "hehe";// cos theer truyeenf vaof String

        User[] usersArray = new User[]{user1, user2}; // mang

        return  Observable.just(usersArray, strDta, user4, listenUser);


        /*
        Tổng kết nếu kiểu dữ liệu array , string , object thì phương thức phải để kiểu <Serializable>
        Trường hopwj đối với List thì phương thức ko để <Serializable>
        nếu List nằm trong với 3 kiểu trên thì ko cần <Serializable>
        trường hiợp là 1 object thì bên model phải implement Serializable nó mới chạy ra object
        * */
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