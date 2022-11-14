package com.llw.kotlintest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
/**
 *    author : Ls
 */
class ThirdActivity : AppCompatActivity() {

    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var int_array:Array<Int> = arrayOf<Int>(1,2,3)
        var float_array:Array<Float> = arrayOf<Float>(1.0f,2.0f,3.0f)
        var double_array:Array<Double> = arrayOf<Double>(3.14,2.54,3.45)

        //普通函数
        fun setArrayNumber(array: Array<Number>) {
            var str: String = "数组元素一次排列"
            for(item in array){
                str = str + item.toString() + ", "
            }
            tv_result.text = str
        }



        //Kotlin进行循环调用时，要求参数类型完全匹配，所以即使Int继承自Number类，也不能调用setArrayNumber方法传送Int类型
        var count = 0
        btn_test.setOnClickListener {
            when(count%3){
                0 -> setArrayStr<Int>(int_array)
                1 -> setArrayStr<Float>(float_array)
                else -> setArrayStr<Double>(double_array)
            }
            count++
        }


    }

    //只有内联函数才能被具体化   请注意，这个inline是全局的，所以不能写在函数方法里面，要写在外面
    inline fun <reified T:Number> setArrayStr(array: Array<T>){
        var str:String ="数组元素依次排列"
        for (item in array){
            str = str + item.toString() + ", "
        }
        tv_result.text = str
    }
}