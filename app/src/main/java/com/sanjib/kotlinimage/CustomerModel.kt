package com.sanjib.kotlinimage


class CustomerModel (var id : String ?= null , var prodname: String, var Name : String,var phone: String ,var Price: String ,var address: String)

class customerModel {

    private var id: String?=null
    var prodname: String? = null
    var Name : String? = null
    var phone: String? = null
    var Price: String? = null
    var address: String? = null


    constructor():this("","","","","","") {

    }


    constructor(prodname: String?, Name: String?,phone: String?,Price: String?,address:String?,id: String?) {
        this.prodname = prodname
        this.Name = Name
        this.phone=phone
        this.Price=Price
        this.address=address
        this.id=id
    }
}