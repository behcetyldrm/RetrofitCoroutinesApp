package com.behcetemreyildirim.retrofitcoroutinesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.behcetemreyildirim.retrofitcoroutinesapp.adapter.CryptoAdapter
import com.behcetemreyildirim.retrofitcoroutinesapp.databinding.ActivityMainBinding
import com.behcetemreyildirim.retrofitcoroutinesapp.model.CryptoModel
import com.behcetemreyildirim.retrofitcoroutinesapp.service.CryptoAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter: CryptoAdapter? = null
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        //coroutine de bir hata meydana gelirse bu mesajı göstereceğiz
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager


        loadData()
    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)


        job = CoroutineScope(Dispatchers.IO).launch {  //internetten veri çekmek için IO kullandık
            val response = retrofit.getData()

            withContext(Dispatchers.Main + exceptionHandler){ //hata olursa exceptionHandler çalışır
                if (response.isSuccessful){

                    response.body()?.let {  //response içerisinden verileri çektik

                        cryptoModels = ArrayList(it)
                        cryptoModels?.let {
                            recyclerViewAdapter = CryptoAdapter(cryptoModels!!)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel() //sayfa kapandığında veriler iptal olur ve bellekten silinir
    }
}