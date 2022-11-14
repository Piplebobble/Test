package com.llw.kotlintest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*

/**
 *    author : Ls
 */
class TwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var stra:String="非空"
        var strb: String?=null
        var strc: String?="可空串"
        var length:Int=0

        btn_a.setOnClickListener{
            length=stra.length
            tv_result.text="字符串a的长度为$length"
        }

        btn_b.setOnClickListener{
            //直接写length=strb.length会出错,所以可空字符串都要先判断
            length= if(strb!=null) strb.length else -1
            tv_result.text="字符串b的长度为$length"
        }

        btn_c.setOnClickListener {
            length= if(strc!=null) strc.length else -1
            tv_result.text="字符串b的长度为$length"
        }


    }
}