package com.example.mob3000_frisbeegolf.api.util

object Parse {
    fun <T> nullOrEmpty(list: List<T>?): Boolean {
        return list == null || list.size <= 0
    }

    fun <T> listAsQuestionMarks(list: List<T>): String {
        val idx = 0
        var str = ""
        for (o in list) str += if (str === "") "? " else ",? "
        return str
    }
}