package com.sunil.roommvvmkotlin.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sunil.roommvvmkotlin.R
import com.sunil.roommvvmkotlin.databinding.ItemListBinding
import com.sunil.roommvvmkotlin.model.Subscriber

class SuscreiberListAdapter(private val subscriverList: List<Subscriber>,private val clickListener:(Subscriber)->Unit):
    RecyclerView.Adapter<SuscreiberListAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding:ItemListBinding=DataBindingUtil.inflate(layoutInflater,R.layout.item_list,parent,false);
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscriverList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriverList[position],clickListener)

    }


    class MyViewHolder(val binding:ItemListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(subscriber: Subscriber,clickListener:(Subscriber)->Unit){
            binding.txtName.text=subscriber.name
            binding.txtEmail.text=subscriber.email
            binding.llListItemLayout.setOnClickListener{
                clickListener(subscriber)
            }
        }
    }



}


