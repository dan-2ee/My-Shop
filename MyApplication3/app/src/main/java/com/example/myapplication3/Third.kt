package com.example.myapplication3

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_third.*

val uri1 : Uri = Uri.parse("android.resource://com.example.myapplication3/drawable/goods1")
val uri2 : Uri = Uri.parse("android.resource://com.example.myapplication3/drawable/goods2")
val uri3 : Uri = Uri.parse("android.resource://com.example.myapplication3/drawable/goods3")
val uri4 : Uri = Uri.parse("android.resource://com.example.myapplication3/drawable/goods4")
val uri5 : Uri = Uri.parse("android.resource://com.example.myapplication3/drawable/goods5")
var ItemList = arrayListOf<DataVo>(
    DataVo("LITTLE FIRE COVY(BLUE)", "7,000WON", uri1),
    DataVo("LITTLE FIRE COVY(YELLOW)", "8,000WON", uri2),
    DataVo("CHERRY COVY(BROWN)", "7,000WON", uri3),
    DataVo("BUBBLE CHICHI(BLACK)", "28,000WON", uri4),
    DataVo("DOT FLOWER BEAR(BLACK)", "28,000WON", uri5),
)
class Third : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val mAdapter = CustomAdapter(this, ItemList)
        recycler_view.adapter = mAdapter

        val layout = LinearLayoutManager(this)
        recycler_view.layoutManager = layout
        recycler_view.setHasFixedSize(true)

        add_item_btn.setOnClickListener {
            val intent= Intent(this,AddItem::class.java)
            startActivity(intent)
        }

        del_item_btn.setOnClickListener {
            mAdapter.removeItem(0)
            //무조건 0번째 아이템 삭제되도록 구현
        }

        show_info_btn.setOnClickListener {
            if (isvaild) {   //로그인 되어있으면
                showInfo()
            }
            else {    //회원이 아니면
                question()
            }
        }
    }

    private fun question() {
        val builder = AlertDialog.Builder(this)
        val ad = builder.create()
        builder.setTitle("Message")
        builder.setMessage("현재 비회원상태입니다. \n'확인' 버튼을 누르면 회원가입 페이지로 이동합니다.")
        builder.setNegativeButton("취소"){dialogInterface: DialogInterface,
                                        i:Int-> ad.dismiss()
        }
        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface,
                                          i:Int-> val intent=Intent(this,Second::class.java)
        startActivity(intent)}
        builder.show()
    }

    private fun showInfo() {
        val sharedPreferences = getSharedPreferences("information", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id${num}","")
        val pw = sharedPreferences.getString("pw${num}","")
        val name = sharedPreferences.getString("name${num}","")
        val phoneNumber = sharedPreferences.getString("phoneNumber${num}","")
        val address = sharedPreferences.getString("address${num}","")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Profile")
        builder.setMessage("ID: ${id}\nPW: ${pw}\nName: ${name}\nPhoneNumber: ${phoneNumber}\nAddress:${address}")
        builder.setPositiveButton("Confirm"){ dialogInterface: DialogInterface, i:Int->}
        builder.show()
    }
}