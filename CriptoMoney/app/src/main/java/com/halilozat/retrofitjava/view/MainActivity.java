package com.halilozat.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.halilozat.retrofitjava.R;
import com.halilozat.retrofitjava.adapter.RecyclerViewAdapter;
import com.halilozat.retrofitjava.model.CryptoModel;
import com.halilozat.retrofitjava.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable; //Uygulamayı tek kullanımlık yapmak için. Hafızayı temizlemeye yarar.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //https://currencyfreaks.com/documentation.html
        //https://api.currencyfreaks.com/latest?apikey=0d40d2bc3420454cb04c95b3ff183ad0
        //https://api.nomics.com/v1/prices?key=a0a90eb786fc3542011f981bb907ceba

        recyclerView = findViewById(R.id.recyclerView);





        //Retrofit && JSON İşlemlerimiz

        Gson gson = new GsonBuilder().setLenient().create(); //setLenient() => JSON'u aldığını gösterir.

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //URL'i al
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //RxJava'yı kullandığımızı bildiriyoruz.
                .addConverterFactory(GsonConverterFactory //API'yi Model içerisinde convert ettiğimizi bildiriyoruz.
                        .create(gson)).build();


        loadData(); //veriyi alma metodu

    }

    private void loadData(){  //veriyi alma metodu
        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io()) //gözlemle
                .observeOn(AndroidSchedulers.mainThread()) //sonuçları getir
                .subscribe(this::handleResponse)); //işle, ele al (handleResponse)


    }
    private void handleResponse(List<CryptoModel> cryptoModelList) {
        cryptoModels = new ArrayList<>(cryptoModelList);

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter); //verileri göstermek
    }

    @Override
    protected void onDestroy() { //olaylar tamamen bittiğinde
        super.onDestroy();

        compositeDisposable.clear(); //tüm API işlemlerini temizle
    }
}