package com.sanjib.kotlinimage

class Model {
    var Image: String? = null
    var Name : String? = null
    var ShortDesc: String? = null
    var Price: String? = null


    constructor():this("","","","") {

    }


    constructor(Image: String?, Name: String?,ShortDesc: String?,Price: String?) {
        this.Image = Image
        this.Name = Name
        this.ShortDesc=ShortDesc
        this.Price=Price
    }
}