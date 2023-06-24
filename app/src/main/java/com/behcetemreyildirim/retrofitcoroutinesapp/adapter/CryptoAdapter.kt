package com.behcetemreyildirim.retrofitcoroutinesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.behcetemreyildirim.retrofitcoroutinesapp.databinding.RecyclerRowBinding
import com.behcetemreyildirim.retrofitcoroutinesapp.model.CryptoModel

class CryptoAdapter(val cryptoList: ArrayList<CryptoModel>): RecyclerView.Adapter<CryptoAdapter.CryptoVH>() {
    class CryptoVH(var binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoVH {
        val view = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
        return CryptoVH(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: CryptoVH, position: Int) {
        holder.binding.textName.text = cryptoList[position].currency
        holder.binding.textPrice.text = cryptoList[position].price
    }
}