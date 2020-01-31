package com.ciel.springcloudnewfatherkotlin.base

class Ciel;

fun main(args: Array<String>) {

    println("hello kotlin");

    var name: String = "xiapeixin";

    var age: Int = 256;

    val sum1 = sum1(9, 9)
    println(sum1)


}

fun sum1(a: Int, b: Int): Int { //函数
    return a + b
}

fun sum2(a: Int, b: Int) =  a + b //简化函数

fun printSum(a: Int, b: Int): Unit { //无返回值,占位符
    println("sum of $a and $b is ${a + b}")
}

fun maxOf(a: Int, b: Int) = if (a > b) a else b //三元

fun parseInt(str: String): Int? {  //当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空。
    return null;
}

fun printProduct(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
    if (x != null && y != null) {
        // 在空检测后，x 与 y 会自动转换为非空值（non-nullable）
        println(x * y)
    }
    else {
        println("'$arg1' or '$arg2' is not a number")
    }
}

fun getStringLength1(obj: Any): Int? { //类型检测与自动类型转换
    if (obj is String) {
        // `obj` 在该条件分支内自动转换成 `String`
        return obj.length
    }
    // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
    return null
}

fun getStringLength2(obj: Any): Int? {
    if (obj !is String) return null

    // `obj` 在这一分支自动转换为 `String`
    return obj.length
}

fun getStringLength3(obj: Any): Int? {
    // `obj` 在 `&&` 右边自动转换成 `String` 类型
    if (obj is String && obj.length > 0) {
        return obj.length
    }
    return null
}