package com.example.myapplication3

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class Second : AppCompatActivity() {
    var isBlank = false   //빈공간 존재 여부
    var idCheck = true   //id 검사 여부
    var pwCheck = false   //pw 검사 여부
    var nameCheck = false   //name 검사 여부
    var clauseCheck = false    //약관 동의 여부
    var phoneCheck = false  //전화번호 검사 여부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        //join text underline
        Join.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        //ID_check_btn click -> 아이디 중복 체크
        ID_Check_btn.setOnClickListener {
            idCheck = true
            val sharedPreferences = getSharedPreferences("information", Context.MODE_PRIVATE)
            val savedCount = sharedPreferences.getInt("count",0)
            for (i:Int in 0..savedCount) {
                val savedID = sharedPreferences.getString("id${i}","")
                val id = Join_ID.text.toString()
                if (id == savedID) {
                    idCheck = false
                }
            }
            if (idCheck) {
                idCheckPopup("success")
            }
            else {
                idCheckPopup("fail")
            }
        }

        //PW_Check_btn click  -> 비밀번호 규칙 체크
        PW_Check_btn.setOnClickListener {
            val pw = Join_PW.text.toString()
            if (pw.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{6,15}\$".toRegex())) {
                pwCheck = true
                pwCheckPopup("success")
            }
            else {
                pwCheck = false
                pwCheckPopup("fail")
            }
        }
        //join btn
        join_complete_Btn.setOnClickListener {
            val id = Join_ID.text.toString()
            val pw = Join_PW.text.toString()
            val name = Join_Name.text.toString()
            val phoneNumber = Join_Phone.text.toString()
            val address = Join_Address.text.toString()

            // 빈공간 있는지 체크
            isBlank = false
            if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()|| address.isEmpty()) {
                isBlank = true
            }
            // 이름 입력 체크 -> 2글자 이상의 한국어만 가능
            nameCheck = false
            if (name.matches("^[ㄱ-ㅎ가-힣]{2,}$".toRegex())) {
                nameCheck = true
            }
            // 전화번호 입력 체크 -> 숫자로만 이루어져 있는지 and 11자리 인지
            phoneCheck = false
            if (phoneNumber.isDigitsOnly() && phoneNumber.length == 11) {
                phoneCheck = true
            }
            // 약관 동의 체크
            if(Accept.isChecked()) {
                clauseCheck = true
            }
            else if (Decline.isChecked()) {
                clauseCheck = false
            }

            if(!isBlank && idCheck && pwCheck && clauseCheck && nameCheck && phoneCheck) {
                val sharedPreferences = getSharedPreferences("information", Context.MODE_PRIVATE)
                var savedCount = sharedPreferences.getInt("count", 0)

                //회원가입 성공 메시지 띄우기
                Toast.makeText(this, "회원가입성공", Toast.LENGTH_SHORT).show()
                //유저가 입력한 id, pwd 등 쉐어드에 저장한다
                val editor = sharedPreferences.edit()
                editor.putString("id${savedCount}", id)
                editor.putString("pw${savedCount}", pw)
                editor.putString("name${savedCount}", name)
                editor.putString("phoneNumber${savedCount}", phoneNumber)
                editor.putString("address${savedCount}", address)
                savedCount+=1
                editor.putInt("count", savedCount)
                editor.apply()

                //첫번째 화면으로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else if(!idCheck){
                Toast.makeText(this, "Check your ID", Toast.LENGTH_SHORT).show()
            }
            else if(isBlank){
                Toast.makeText(this, "Empty space exists", Toast.LENGTH_SHORT).show()
            }
            else if(!pwCheck) {
                Toast.makeText(this, "Check your Password", Toast.LENGTH_SHORT).show()
            }
            else if(!clauseCheck) {
                Toast.makeText(this, "약관에 동의해주세요 ", Toast.LENGTH_SHORT).show()
            }
            else if(!nameCheck) {
                Toast.makeText(this, "Enter your name correctly", Toast.LENGTH_SHORT).show()
            }
            else if(!phoneCheck) {
                Toast.makeText(this, "Enter only numbers without -", Toast.LENGTH_SHORT).show()
            }
        }

        //back btn 클릭하면 첫화면으로 돌아감
        join_back_Btn.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        //약관보기 버튼 클릭하면 약관 내용 보여주는 함수 호출
        show_Btn.setOnClickListener {
            showPopup()
        }
    }

    private fun idCheckPopup(msg: String) {
        val dialog = AlertDialog.Builder(this)

        if(msg.equals("success")) {
            dialog.setTitle("Message")
            dialog.setMessage("사용 가능한 아이디입니다 ")
        }
        else if(msg.equals("fail")) {
            dialog.setTitle("Message")
            dialog.setMessage("중복된 아이디입니다 ")
        }
        dialog.setPositiveButton("Close"){ dialogInterface: DialogInterface, i:Int->}
        dialog.show()
    }

    private fun pwCheckPopup(msg: String) {
        val dialog = AlertDialog.Builder(this)

        if(msg.equals("success")) {
            dialog.setTitle("Message")
            dialog.setMessage("사용 가능한 비밀번호입니다 ")
        }
        else if(msg.equals("fail")) {
            dialog.setTitle("Message")
            dialog.setMessage("잘못된 비밀번호 입니다." + "\n" + "숫자, 문자, 특수문자 중 2가지 이상 포함하여 6-15자리로 입력해주세요 ")
        }
        dialog.setPositiveButton("Close"){ dialogInterface: DialogInterface, i:Int->}
        dialog.show()
    }

    private fun showPopup() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("개인정보 약관 동의")
        builder.setMessage("<개인정보보호 포털> 개인정보 처리방침\n" +
                "개인정보보호위원회가 취급하는 모든 개인정보는 관련 법령에 근거하여 수집 · 보유 및 처리되고 있습니다. 「개인정보보호법」은 이러한 개인정보의 취급에 대한 일반적 규범을 제시하고 있으며, 개인정보보호위원회는 이러한 법령의 규정에 따라 수집 · 보유 및 처리하는 개인정보를 공공업무의 적절한 수행과 이용자의 권익을 보호하기 위해 적법하고 적정하게 취급할 것입니다.\n" +
                "\n" +
                "또한, 개인정보보호위원회는 관련 법령에서 규정한 바에 따라 보유하고 있는 개인정보에 대한 열람, 정정·삭제, 처리정지 요구 등 이용자의 권익을 존중하며, 이용자는 이러한 법령상 권익의 침해 등에 대하여 행정심판법에서 정하는 바에 따라 행정심판을 청구할 수 있습니다.\n" +
                "\n" +
                "개인정보보호위원회는 개인정보보호법 제 30조에 따라 정보주체의 개인정보 보호 및 권익을 보호하고 개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 개인정보 처리방침을 수립 · 공개하고 있습니다.")
        builder.setPositiveButton("확인"){dialogInterface: DialogInterface, i:Int->}
        builder.show()
    }

}