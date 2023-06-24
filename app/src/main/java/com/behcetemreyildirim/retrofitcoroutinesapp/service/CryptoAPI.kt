package com.behcetemreyildirim.retrofitcoroutinesapp.service

import com.behcetemreyildirim.retrofitcoroutinesapp.model.CryptoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData(): Response<List<CryptoModel>>
    //coroutines ile kullanmak için suspend fun yaptık ve call yerine cevapların alınacağı response kullandık

    /*
        coroutines ile asenkron çalıştığımız için call kullanmamıza gerek kalmaz. call'un enqeue özelliği ile asenkron çalışıyorduk.
        Response kullanma nedenimiz coroutines içindir.
    */
}