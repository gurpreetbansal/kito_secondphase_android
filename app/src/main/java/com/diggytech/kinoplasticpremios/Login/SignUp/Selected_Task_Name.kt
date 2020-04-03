package com.diggytech.kinoplasticpremios.Login.SignUp

class Selected_Task_Name {

    var name: String? = null
    var extention_name: String? = null
    var file_id: Int = 0

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getExtenName(): String {
        return extention_name.toString()
    }

    fun setExtenName(extention_name: String) {
        this.extention_name = extention_name
    }
    fun getId(): Int {
        return file_id
    }
    fun setId(file_id: Int) {
        this.file_id = file_id;
    }



}
