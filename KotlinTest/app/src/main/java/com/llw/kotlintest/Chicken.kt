package com.llw.kotlintest

/**
 *    author : Ls
 */
abstract class Chicken (name:String,sex:Int,var voice:String):AnimalMain(name, sex){
    val numberArray:Array<String> = arrayOf("一","二","三","四","五","六","七")
    abstract fun callOut(times:Int):String
}