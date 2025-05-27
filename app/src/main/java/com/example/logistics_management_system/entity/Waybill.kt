package com.example.logistics_management_system.entity

class Waybill constructor(
        id:String,               //编号
        departureStation:String, //始发站
        destination:String,      //到站
        consigner:String,        //发货人
        consignerPhone:String,   //发货电话
        consignee:String,        //收货人
        consigneePhone:String,   //收货电话
        goodsName:String,        //货物名称
        number:String,           //件数
        paidFreight:String,      //已付运费
        unpaidFreight:String){   //到付运费

    val id = id
    val departureStation = departureStation
    var destination = destination
    var consigner = consigner
    var consignerPhone = consignerPhone
    var consignee = consignee
    var consigneePhone = consigneePhone
    var goodsName = goodsName
    var number = number
    var paidFreight = paidFreight
    var unpaidFreight = unpaidFreight
}