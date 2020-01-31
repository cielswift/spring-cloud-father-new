package com.ciel.springcloudnewfatherkotlin.base

class People {
    lateinit var name : String

    var age : Int = 0

    override fun toString(): String {
        return name + ":" + age
    }
}

fun main(args: Array<String>) {

    val p = People()

    p.name = "xiapeixin"
    p.age = 99

    println(p)


}