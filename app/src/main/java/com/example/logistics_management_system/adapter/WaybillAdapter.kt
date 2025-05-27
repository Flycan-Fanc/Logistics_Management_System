package com.example.logistics_management_system.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.logistics_management_system.R
import com.example.logistics_management_system.entity.Waybill

class WaybillAdapter(val waybillList: List<Waybill>) : RecyclerView.Adapter<WaybillAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val waybillShow: TextView = view.findViewById(R.id.Waybill)  //获取显示文本控件，需要改
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.waybill_item, parent, false)
        return ViewHolder(view)
    }       //加载布局

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val waybill = waybillList[position]
        //显示文本设置
        /*holder.waybillShow.text = waybill.destination + "—沈阳  " + waybill.goodsName +
                waybill.number + "件  No:" + waybill.id +
                "  收货人:" + waybill.consignee + "(" + waybill.consigneePhone + ") 到付：" +
                waybill.unpaidFreight + "元"*/
        holder.waybillShow.setText("${waybill.destination}—${waybill.departureStation}  ${waybill.goodsName}${waybill.number}件\n" +
                "No:${waybill.id} 收货人:${waybill.consignee}\n(电话:${waybill.consigneePhone}) 到付:" +
                "${waybill.unpaidFreight}元")
        /*holder.waybillShow.text = "？？？？"*/
    }

    override fun getItemCount() = waybillList.size
}
