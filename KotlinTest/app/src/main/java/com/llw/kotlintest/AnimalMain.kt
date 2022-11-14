package com.llw.kotlintest

import android.content.Context
import android.widget.Toast

/**
 *    author : Ls
 */

//二级构造函数从属于主构造函数，如果使用二级构造函数声明该类的实例，系统就会先调用主构造函数的init代码，再调用二级构造函数的自身代码
//通过二级构造函数声明实例有一个问题，就是toast会弹窗两次，因为主构造函数的init方法已经弹窗，然后二级构造函数自身再次弹窗，那能不能不调用主构造函数呢？
//为了解决该问题，Kotlin设定了主构造函数时不是必需的，也就是说类可以把几个构造函数都放在类内部定义，从而都变成二级构造函数，如此就去掉了主构造函数

//这样写就没有主构造函数了，都是二级构造函数，直接使用即可，函数之间没有从属关系，不存在重复调用。
//构造函数可以有默认参数

//Kotlin的类型默认是不能继承的(即 final类型),如果需要继承某类，该父类就应当声明open类型
open class AnimalMain (var name:String,val sex:Int = MALE){
    //变量、方法、类默认都是public，所以一般都把public省略掉了
    var sexName:String
    init {
        sexName = getSexName(sex)
    }

    //私有的方法既不能被外部访问，也不能被子类继承，因此open与private不能共存,否则编译器会报错
    open protected fun getSexName(sex:Int):String{
        return if(sex == MALE) "公" else "母"
    }

    fun getDesc(tag:String):String{
        return "欢迎来到$tag: 这只${name}是${sexName}的。"
    }

    companion object BirdStatic{
        val MALE = 0
        val FEMALE = 1
        val UNKOWN = -1
        fun judgeSex(sexName:String):Int {
            var sex:Int = when (sexName){
                "公","雄" -> MALE
                "母","雌" -> FEMALE
                else -> UNKOWN
            }
            return sex
        }
    }
}


fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.longToast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

