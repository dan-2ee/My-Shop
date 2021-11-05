package com.example.myapplication3

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

var isvaild = false
var num = 0
class MainActivity : AppCompatActivity() {
    var isLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // login 기능
        Login_Btn.setOnClickListener {
            //edit text로부터 입력된 값을 받아옴
            isLogin = false
            var id = ID.text.toString()
            var pw = PW.text.toString()

            val sharedPreferences = getSharedPreferences("information", Context.MODE_PRIVATE)
            val savedCount = sharedPreferences.getInt("count",0)
            for (i:Int in 0..savedCount) {
                val savedID = sharedPreferences.getString("id${i}","")
                val savedPW = sharedPreferences.getString("pw${i}","")

                if (id=="" || pw=="") {   //id, pw is blank
                    isLogin = false
                    break
                }
                //id, pw 입력이 정상이면 세번째 페이지 이동
                if (id==savedID && pw==savedPW) {
                    isLogin = true
                    num = i
                    val intent= Intent(this,Third::class.java)
                    startActivity(intent)
                    break
                }
            }

            if (!isLogin) {
                loginPopup()
            }
            if (isLogin) isvaild = true
            else if (!isLogin) isvaild = false
        }
        //join btn click -> 두번쨰 페이지
        Join_Btn.setOnClickListener{
            val intent= Intent(this,Second::class.java)
            startActivity(intent)
        }
        //goods btn click -> 세번째 페이지
        Goods_Btn.setOnClickListener{
            val intent= Intent(this,Third::class.java)
            startActivity(intent)
        }
    }
    private fun loginPopup() {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Message")
        dialog.setMessage("존재하지 않는 아이디 혹은 비밀번호입니다")
        dialog.setPositiveButton("Close"){ dialogInterface: DialogInterface, i:Int->}
        dialog.show()
    }

}