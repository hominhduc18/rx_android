package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    private DataAdapter mDataAdapter;
    private RecyclerView rcvData;

    private List<ObjectData> mListData;

    private  Disposable disposable;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btncallApi = findViewById(R.id.btn_call_api);
        rcvData = findViewById(R.id.rc_data); // Update this line

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvData.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvData.addItemDecoration(dividerItemDecoration);

        mListData = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        btncallApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickCallApi();
            }
        });
    }


    @SuppressLint("CheckResult")
    private void onclickCallApi(){
//        mDataAdapter = new DataAdapter(getListData());
//        rcvData.setAdapter(mDataAdapter);

        progressDialog.show();
        APIService.apiservice.callApi()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ObjectData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }
                    @Override
                    public void onNext(@NonNull List<ObjectData> objectData) {
                            mListData = objectData;
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(MainActivity.this, "Call API ERRO",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "Call API SUCCESSS",Toast.LENGTH_SHORT).show();

                        mDataAdapter = new DataAdapter(mListData);
                        rcvData.setAdapter(mDataAdapter);

                        progressDialog.dismiss();
                    }
                });
    }

  protected void onDestroy(){
        super.onDestroy();
        if(disposable !=null){
            disposable.dispose();
        }
  }
    //    private List<ObjectData> getListData(){
//        List<ObjectData> list = new ArrayList<>();
//
//        list.add(new ObjectData("title 1", "body 1"));
//        list.add(new ObjectData("title 2", "body 2"));
//        list.add(new ObjectData("title 3", "body 3"));
//        list.add(new ObjectData("title 4", "body 4"));
//        list.add(new ObjectData("title 5", "body 5"));
//
//
//
//        return list;
//    }
}