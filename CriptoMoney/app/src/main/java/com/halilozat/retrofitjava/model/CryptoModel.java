package com.halilozat.retrofitjava.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

@SerializedName("currency") //gelen dataları okumayı sağlar. (isim aynı olmak zorunda)
public String currency;

@SerializedName("price") //gelen dataları okumayı sağlar. (isim aynı olmak zorunda)
public String price;

}
