package com.example.logistics_management_system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MenuActivity : AppCompatActivity() {
    lateinit var timeChangeReceiver: TimeChangeReceiver
    lateinit var timeShow: TextView
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val inputButton = findViewById<Button>(R.id.button3)
        val queryLocalButton = findViewById<Button>(R.id.button4)
        val xmlButton = findViewById<Button>(R.id.button5)
        val jsonButton = findViewById<Button>(R.id.button6)
        val changeUserButton = findViewById<Button>(R.id.button7)
        val quitButton = findViewById<Button>(R.id.button8)

        val username1 = findViewById<TextView>(R.id.textView6)
        val password1 = findViewById<TextView>(R.id.textView8)

        timeShow = findViewById<TextView>(R.id.textView9)
        val name1 = intent.getStringExtra("myName").toString()
        val pwd1 = intent.getStringExtra("myPWD").toString()


        username1.text = "我是${(name1.toString())}"
        password1.text = "我的密码是${(pwd1.toString())}"

        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        timeShow.text = "我当前时间是:\n${currentTime}\n"
        //创建一个intentFilter,用于指定要监听的广播类型
        val intentFilter = IntentFilter()
        //向intentFilter对象中添加一个过滤器，监听系统时间的变化
        intentFilter.addAction("android.intent.action.TIME_TICK")
        //创建一个TimeChangeReceiver对象，用于指定要监听的广播类型
        timeChangeReceiver = TimeChangeReceiver()
        //注册timeChangeReceiver对象，使其能够接收系统广播
        registerReceiver(timeChangeReceiver, intentFilter)
        //定期更新TextView的文本
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                timeShow.text = "当前时间是:\n${currentTime}\n"
                handler.postDelayed(this, 1000) // 1 秒钟后再次运行
            }
        })
        //baidu.text = "我当前时间是:\n${getNowTime()}\n点我可进入百度搜索"

        /*timeShow.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.baidu.com")
            startActivity(intent)
        }*/

        inputButton.setOnClickListener {
            val inputIntent = Intent(this,InputWaybillActivity::class.java)
            startActivity(inputIntent)
        }

        queryLocalButton.setOnClickListener {
            val queryLocalIntent = Intent(this,QueryLocalWaybillActivity::class.java)
            startActivity(queryLocalIntent)
        }

        xmlButton.setOnClickListener {
            val xmlIntent = Intent(this,QueryXMLWaybillActivity::class.java)
            startActivity(xmlIntent)
        }

        jsonButton.setOnClickListener {
            val jsonIntent = Intent(this,QueryJSonWaybillActivity::class.java)
            startActivity(jsonIntent)
        }

        changeUserButton.setOnClickListener {
            val mainIntent = Intent(this,MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }

        quitButton.setOnClickListener{
            finish()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        //注销timeChangeReceiver
        unregisterReceiver(timeChangeReceiver)
        //删除所有未处理的消息
        handler.removeCallbacksAndMessages(null)
    }
    inner class TimeChangeReceiver : BroadcastReceiver() {
        //重写onReceiver方法，接收到广播事件时会调用该方法
        override fun onReceive(context: Context, intent: Intent) {
        }
    }

    /*private fun getNowTime(): String {
        val date = Date()
        val dateFormat = DateFormat.getDateTimeInstance()
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }*/
}