package com.example.logistics_management_system

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logistics_management_system.adapter.WaybillAdapter
import com.example.logistics_management_system.dao.DatabaseHelper
import com.example.logistics_management_system.entity.Waybill

class QueryLocalWaybillActivity : AppCompatActivity() {
    private val waybillList = ArrayList<Waybill>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_local_waybill)
        initWaybills()
        val layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.localWaybillRecyclerView)
        val buttonReturn = findViewById<Button>(R.id.buttonRetFromLocalWaybill)
        recyclerView.layoutManager = layoutManager
        val adapter = WaybillAdapter(waybillList)
        recyclerView.adapter = adapter

        buttonReturn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("Range")
    private fun initWaybills(){
        //在这里写在本地数据库查询数据，添加到waybillList的代码
        val dbHelperWaybill = DatabaseHelper(this,"LMSDatabase.db",1)
        val db = dbHelperWaybill.writableDatabase
        val cursor = db.query(
            "Waybill",
            null,
            "waybill_id is not null",
            null,
            null,
            null,
            null
        )
        if(cursor.moveToFirst()){
            do{
                //遍历Cursor对象，将数据存入waybillList
                val id = cursor.getString(cursor.getColumnIndex("waybill_id"))
                val departureStation = "沈阳"
                val destination = cursor.getString(cursor.getColumnIndex("destination"))
                val consigner = cursor.getString(cursor.getColumnIndex("consigner"))
                val consignerPhone = cursor.getString(cursor.getColumnIndex("consignerPhone"))
                val consignee = cursor.getString(cursor.getColumnIndex("consignee"))
                val consigneePhone = cursor.getString(cursor.getColumnIndex("consigneePhone"))
                val goodsName = cursor.getString(cursor.getColumnIndex("goodsName"))
                val number = cursor.getInt(cursor.getColumnIndex("number")).toString()
                val paidFreight = cursor.getInt(cursor.getColumnIndex("paidFreight")).toString()
                val unpaidFreight = cursor.getInt(cursor.getColumnIndex("unpaidFreight")).toString()
                waybillList.add(Waybill(id,departureStation,destination,consigner,consignerPhone,consignee,
                    consigneePhone,goodsName,number,paidFreight,unpaidFreight))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}