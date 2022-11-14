package com.llw.guidepagedemo.tools;
//汉字剪裁工具
/**
 * author : Ls
 */
public class TextCut {
    public static int TextLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {

            /* 获取一个字符 */

            String temp = value.substring(i, i + 1);

            /* 判断是否为中文字符 */

            if (temp.matches(chinese)) {

                /* 中文字符长度为2 */

                valueLength += 2;

            } else {

                /* 其他字符长度为1 */

                valueLength += 1;

            }

        }

        return valueLength;

    }


    public static String getSubString(String str, int length) {
        int count = 0;
        int offset = 0;
        char[] c = str.toCharArray();
        int size = c.length;
        if(size >= length){
            for (int i = 0; i < c.length; i++) {
                if (c[i] > 256) {
                    offset = 2;
                    count += 2;
                } else {
                    offset = 1;
                    count++;
                }
                if (count == length) {
                    return str.substring(0, i + 1);
                }
                if ((count == length + 1 && offset == 2)) {
                    return str.substring(0, i);
                }
            }
        }else{
            return str;
        }
        return "";
    }
}

