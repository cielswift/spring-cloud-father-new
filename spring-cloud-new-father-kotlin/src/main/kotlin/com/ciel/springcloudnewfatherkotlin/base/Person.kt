package com.ciel.springcloudnewfatherkotlin.base

import java.math.BigDecimal
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Person

const val NAME= "XIAPEIXIN"

fun main(args :Array<String>) {

    val items = listOf("apple", "banana", "kiwifruit")

    for (item in items) {
        println(item)
    }
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }

    val  j = jiecheng(11)

    print(j)

    var a = 0x7fffffff;
    var b = 2147483647;

    println(a == b)


    val lambda = lambda(::hs, "5")
    println(lambda)

    val money = BigDecimal("96.56")

    val forName = Class.forName("com.ciel.springcloudnewfatherkotlin.base.Person")
    val newInstance = forName.constructors.first().newInstance()

    val final = """夏培鑫\n"""
    val final2 = "夏培鑫\n"
    println(final+ final2)

    val lines =
            Files.lines(Path.of("C:\\ciel\\spring\\spring-cloud-new-sso\\consumer\\log_debug.log"), Charset.forName("UTF-8"))

    lines.filter { t -> t.contains("1579893036372") }.forEach(::println)


}
fun jiecheng(x: Int) : Int{

    if(x<=1){
        return   1
    }else{
        return x * jiecheng(x-1)
    }
}

fun lambda(ax : (s: String)-> Int,tr: String): String{ //函数作为参数
    return ax(tr).toString()
}

fun hs(s: String):Int{
    return s.toInt();
}

fun describe(obj: Any): String =
        when (obj) {
            1          -> "One"
            "Hello"    -> "Greeting"
            is Long    -> "Long"
            !is String -> "Not a string"
            else       -> "Unknown"
        }