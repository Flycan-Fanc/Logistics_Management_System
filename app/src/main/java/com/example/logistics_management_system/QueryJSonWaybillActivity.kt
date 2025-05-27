package com.example.logistics_management_system

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logistics_management_system.adapter.WaybillAdapter
import com.example.logistics_management_system.entity.Waybill
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class QueryJSonWaybillActivity : AppCompatActivity() {
    private val waybillList = ArrayList<Waybill>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_json_waybill)
        val layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.jsonWaybillRecyclerView)
        val buttonReturn = findViewById<Button>(R.id.buttonRetFromJson)

        //子线程
        val thread1 = Thread {
            //在这里写从JSon解析数据添加到waybillList的代码
            // 创建URL对象
            val url = URL("http://218.25.17.139/simulated-Waybills-db.json")

            // 建立连接
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            // 获取响应码
            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                val inputStreamReader = InputStreamReader(connection.inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val response = StringBuilder()

                var inputLine: String?

                // 逐行读取响应数据并打印
                while (bufferedReader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                    //println(inputLine)
                }

                val newResponse = response.slice(21 until (response.length - 1)).toString()
                    .replace(" ","")
                //println(newResponse)

                //解析json格式数据
                try {
                    val jsonArray = JSONArray(newResponse)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val id = jsonObject.getString("waybillNo")
                        val departureStation = jsonObject.getString("transportationDepartureStation")
                        val destination = jsonObject.getString("transportationArrivalStation")
                        val consigner = jsonObject.getString("consignor")
                        val consignerPhone = jsonObject.getString("consignorPhoneNumber")
                        val consignee = jsonObject.getString("consignee")
                        val consigneePhone = jsonObject.getString("consigneePhoneNumber")
                        val goodsName = jsonObject.getString("goodsName")
                        val number = jsonObject.getString("numberOfPackages")
                        val paidFreight = jsonObject.getString("freightPaidByConsignor")
                        val unpaidFreight = jsonObject.getString("freightPaidByTheReceivingParty")

                        synchronized(waybillList) { // 确保在多线程环境下对 waybillList 的操作是安全的
                            waybillList.add(
                                Waybill(
                                    id.replace(" ", ""), //去掉空格
                                    departureStation.replace(" ",""),
                                    destination.replace(" ", ""),
                                    consigner.replace(" ", ""),
                                    consignerPhone.replace(" ", ""),
                                    consignee.replace(" ", ""),
                                    consigneePhone.replace(" ", ""),
                                    goodsName.replace(" ", ""),
                                    number.replace(" ", ""),
                                    paidFreight.replace(" ", ""),
                                    unpaidFreight.replace(" ", "")
                                )
                            )
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                bufferedReader.close()
            } else {
                // 响应码不为HTTP_OK，请求失败
                println("HTTP GET 请求失败，错误码：$responseCode")
            }
            // 关闭连接
            connection.disconnect()
        }
        thread1.start()
        thread1.join() //阻塞主线程

        recyclerView.layoutManager = layoutManager
        val adapter = WaybillAdapter(waybillList)
        recyclerView.adapter = adapter

        buttonReturn.setOnClickListener {
            finish()
        }
    }

}