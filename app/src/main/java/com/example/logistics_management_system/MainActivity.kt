package com.example.logistics_management_system

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.logistics_management_system.dao.DatabaseHelper
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //控件绑定
        var userName = findViewById<EditText>(R.id.editUseName)
        var userPWD = findViewById<EditText>(R.id.editTextTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonQuit = findViewById<Button>(R.id.buttonQuit)
        //数据库建立
        val dbHelperUser = DatabaseHelper(this,"LMSDatabase.db",1)
        val db = dbHelperUser.writableDatabase
        //键入初始用户，第一次正常运行，之后注释掉
        /*val userValue1  = ContentValues().apply{
            put("user_name","20206043")
            put("user_password","12345")
        }
        db.insert("User",null,userValue1)
        val userValue2  = ContentValues().apply{
            put("user_name","20205992")
            put("user_password","12345")
        }
        db.insert("User",null,userValue2)*/
        /*val userValue1  = ContentValues().apply{
            put("user_name","admin")
            put("user_password","12345")
        }
        db.insert("User",null,userValue1)
        val userValue2  = ContentValues().apply{
            put("user_name","user1")
            put("user_password","123")
        }
        db.insert("User",null,userValue2)
        val userValue3  = ContentValues().apply{
            put("user_name","user2")
            put("user_password","123")
        }
        db.insert("User",null,userValue3)
        val userValue4  = ContentValues().apply{
            put("user_name","user3")
            put("user_password","123")
        }
        db.insert("User",null,userValue4)*/

        //登录按钮点击监听
        buttonLogin.setOnClickListener {
            var result = findUser(userName.text.toString(),userPWD.text.toString())
            if(result==1){
                //没有该用户，清空用户名和密码
                Toast.makeText(getApplicationContext(),"没有该用户",Toast.LENGTH_SHORT).show()
                userName.setText("")
                userPWD.setText("")
            }else if(result==2){
                //密码错误,只清空密码框
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show()
                userPWD.setText("")
            }else if(result==0){
                //登陆成功
                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show()
                //页面跳转
                val mainIntent = Intent(this,MenuActivity::class.java)
                mainIntent.putExtra("myName",userName.getText().toString())
                mainIntent.putExtra("myPWD",userPWD.getText().toString())
                startActivity(mainIntent)
                finish()
            }
        }

        //退出按钮点击监听
        buttonQuit.setOnClickListener {
            finish()
        }
    }


    @SuppressLint("Range")
    fun findUser(userName:String, userPWD:String):Int{  //数据库查询，查询是否存在用户
        val dbHelperUser = DatabaseHelper(this,"LMSDatabase.db",1)
        val db = dbHelperUser.writableDatabase
        val selectionArgs = arrayOf(userName)
        val cursor = db.query(
            "User",
            null,
            "user_name=?",
            selectionArgs,
            null,
            null,
            null
        )
        if(cursor.moveToFirst()){
            //查询到用户，开始验证密码
            val PWD = cursor.getString(cursor.getColumnIndex("user_password"))
            if(PWD.equals(userPWD)){
                return 0
            } else return 2
        }
        else return 1 //未查询到用户
    }


}