package com.example.logistics_management_system

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.logistics_management_system.dao.DatabaseHelper

class InputWaybillActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_waybill)
        //数据库
        val dbHelperWaybill = DatabaseHelper(this, "LMSDatabase.db",1)
        //控件绑定
        val editDestination = findViewById<EditText>(R.id.editDestination)
        val editConsigner = findViewById<EditText>(R.id.editConsigner)
        val editConsignerPhone = findViewById<EditText>(R.id.editConsignerPhone)
        val editConsignee = findViewById<EditText>(R.id.editConsignee)
        val editConsigneePhone = findViewById<EditText>(R.id.editConsigneePhone)
        val editGoodsName = findViewById<EditText>(R.id.editGoodsName)
        val editNumber = findViewById<EditText>(R.id.editNumber)
        val editPaidFreight = findViewById<EditText>(R.id.editPaidFreight)
        val editUnpaidFreight = findViewById<EditText>(R.id.editUnpaidFreight)
        val buttonReturn = findViewById<Button>(R.id.buttonReturnFromInput)
        val buttonSave = findViewById<Button>(R.id.buttonSave)


        //向数据库插入waybill数据
        buttonSave.setOnClickListener {
            //必填字段判断是否为空
            var isNull = 0
            if(editDestination.text.isEmpty()) isNull=1
            if(editGoodsName.text.isEmpty()) isNull=1
            if(editNumber.text.isEmpty()) isNull=1
            if(isNull==0){
                val db = dbHelperWaybill.writableDatabase
                val values = ContentValues().apply{
                    put("destination",editDestination.text.toString())
                    put("consigner",editConsigner.text.toString())
                    put("consignerPhone",editConsignerPhone.text.toString())
                    put("consignee",editConsignee.text.toString())
                    put("consigneePhone",editConsigneePhone.text.toString())
                    put("goodsName",editGoodsName.text.toString())
                    put("number",editNumber.text.toString().toInt())
                    put("paidFreight",editPaidFreight.text.toString().toInt())
                    put("unpaidFreight",editUnpaidFreight.text.toString().toInt())
                }
                db.insert("Waybill",null,values)
                Toast.makeText(getApplicationContext(), "录入数据成功", Toast.LENGTH_SHORT).show()
                isNull = 0
                //清空输入栏
                editDestination.setText("")
                editConsigner.setText("")
                editConsignerPhone.setText("")
                editConsignee.setText("")
                editConsigneePhone.setText("")
                editGoodsName.setText("")
                editNumber.setText("")
                editPaidFreight.setText("")
                editUnpaidFreight.setText("")

            } else{
                Toast.makeText(getApplicationContext(), "标“*”字段为必填项，不能为空", Toast.LENGTH_SHORT).show()
                isNull = 0
            }
        }

        buttonReturn.setOnClickListener {
            finish()
        }
    }




}