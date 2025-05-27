package com.example.logistics_management_system

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logistics_management_system.adapter.WaybillAdapter
import com.example.logistics_management_system.entity.Waybill
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class QueryXMLWaybillActivity : AppCompatActivity() {
    private var waybillList = ArrayList<Waybill>()
    //val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.xmlWaybillRecyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_xml_waybill)
        //initWaybills()
        val layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.xmlWaybillRecyclerView)
        val buttonReturn  = findViewById<Button>(R.id.buttonRetFromXML)

        val thread = Thread {
            val url = URL("http://218.25.17.139/simulated-Waybills-db.xml")

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val xmlData = bufferedReader.readText()

                val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlData.byteInputStream())
                val rootElement = document.documentElement

                val nodeList = rootElement.childNodes
                for (i in 0 until nodeList.length) {
                    val node = nodeList.item(i)
                    if (node.nodeType == org.w3c.dom.Node.ELEMENT_NODE) {
                        val element = node as org.w3c.dom.Element
                        val nodeName = element.nodeName
                        val nodeValue = element.textContent

                        val lines = nodeValue.split("\n")
                        val id = lines[1]
                        val departureStation = lines[6]
                        val destination = lines[7]
                        val consigner = lines[2]
                        val consignerPhone = lines[3]
                        val consignee = lines[4]
                        val consigneePhone = lines[5]
                        val goodsName = lines[9]
                        val number = lines[10]
                        val paidFreight = lines[12]
                        val unpaidFreight = lines[11]

                        println("id: $id")
                        println("departureStation: $departureStation")
                        println("destination: $destination")
                        println("consigner: $consigner")
                        println("consignerPhone: $consignerPhone")
                        println("consignee: $consignee")
                        println("consigneePhone: $consigneePhone")
                        println("goodsName: $goodsName")
                        println("number: $number")
                        println("paidFreight: $paidFreight")
                        println("unpaidFreight: $unpaidFreight")

                        synchronized(waybillList) { // 确保在多线程环境下对 waybillList 的操作是安全的
                            waybillList.add(
                                Waybill(
                                    id.replace(" ", ""), //去掉空格
                                    departureStation.replace(" ", ""),
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
                }
            } else {
                //println("HTTP request failed with response code: $responseCode")
            }

            // 在这里可以使用获得的 waybillList 进行后续操作
        }
        thread.start()
        thread.join() //阻塞主线程

        println(waybillList[1].id)

        recyclerView.layoutManager = layoutManager
        val adapter = WaybillAdapter(waybillList)
        recyclerView.adapter = adapter


        //返回按钮点击监听
        buttonReturn.setOnClickListener {
            finish()
        }
    }



}