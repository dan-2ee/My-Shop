package com.example.myapplication3

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.android.synthetic.main.recyclerview.*
import java.util.jar.Manifest

class AddItem : AppCompatActivity(){
    //val PERM_STORAGE = 9
    val REQ_GALLERY = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        val mAdapter = CustomAdapter(this, ItemList)
        // item select btn Click ->
        Item_Select_btn.setOnClickListener {
            openGallery()
        }
        Item_Check_btn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("itemList", Context.MODE_PRIVATE)
            val currentUri = sharedPreferences.getString("uri","")

            val uri : Uri = Uri.parse(currentUri)
            //사용자가 입력한 name, price 추가
            val name = item_name.text.toString()
            val price = item_price.text.toString() + "WON"
            mAdapter.addItem(DataVo(name, price,uri))
            //addItem(DataVo(name, price,uri))
            //notifyDataSetChanged()
            //mPosition+=1

            val intent= Intent(this,Third::class.java)
            startActivity(intent)
        }
        Item_back_btn.setOnClickListener {
            val intent= Intent(this,Third::class.java)
            startActivity(intent)
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_GALLERY)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val sharedPreferences = getSharedPreferences("itemList", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data.toString()
        editor.putString("uri", uri)
        editor.apply()
        Item_Select_btn.setImageURI(uri.toUri())
    }
}