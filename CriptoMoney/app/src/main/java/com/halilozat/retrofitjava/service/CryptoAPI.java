package com.halilozat.retrofitjava.service;

import com.halilozat.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //GET işlemleri burada yapılır.
    //https://api.nomics.com/v1/prices?key=a0a90eb786fc3542011f981bb907ceba

    @GET("prices?key=a0a90eb786fc3542011f981bb907ceba")
    Observable<List<CryptoModel>> getData();
}
