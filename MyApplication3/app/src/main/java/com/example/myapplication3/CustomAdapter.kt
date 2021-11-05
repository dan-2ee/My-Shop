package com.example.myapplication3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

var mPosition = 0
class CustomAdapter(private val context: Context, private val ItemList: ArrayList<DataVo>):RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {


    fun getPosition(): Int {
        return mPosition
    }

    fun setPosition(position: Int) {
        mPosition = position
    }

    fun addItem(dataVo: DataVo) {
        ItemList.add(dataVo)
        setPosition(mPosition+1)
        notifyDataSetChanged()   //갱신처리
    }

    fun removeItem(position: Int) {
        ItemList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val userPhoto = itemView.findViewById<ImageView>(R.id.goods1)
        private val userName = itemView.findViewById<TextView>(R.id.goods1_name)
        private val userPay = itemView.findViewById<TextView>(R.id.goods1_price)

        fun bind(dataVo: DataVo) {
            //TextView에 데이터 세팅
            userPhoto.setImageURI(dataVo.photo)
            userName.text = dataVo.name
            userPay.text = dataVo.pay
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(ItemList[position])
        holder.itemView.setOnClickListener {view -> setPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return ItemList.size
    }
}

