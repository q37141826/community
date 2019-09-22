package com.qixiu.intelligentcommunity.utlis;

/**
 * Created by HuiHeZe on 2017/8/24.
 */

public class ChineseNumUtil {
    private static String chinese[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "佰", "仟", "萬", "億"};
    private static String simpleChinese[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿"};


    public static String changeChineseSentenceNumToInteger(String str) {
        for (int i = 0; i < chinese.length; i++) {
            str.replace(chinese[i], i + "");
        }
        return str;
    }

    public static String changeSimpleChineseSentenceNumToInteger(String str) {
        for (int i = 0; i < simpleChinese.length; i++) {
            str.replace(simpleChinese[i], i + "");
        }
        return str;
    }

    public static String changeIntegerNumSentenceToChinese(String num) {
        for (int i = 0; i < simpleChinese.length; i++) {
            num.replace(i + "", simpleChinese[i]);
        }
        return num;
    }

    public static String changeIntegerNumSentenceToSimpleChinese(String num) {
        for (int i = 0; i < simpleChinese.length; i++) {
            num.replace(simpleChinese[i], i + "");
        }
        return num;
    }

    public static int changeChineseCharToInteger(String str) {
        if (str.length() > 1) {
            ToastUtil.toast("长度过长");
            return 0;
        }
        for (int i = 0; i < chinese.length; i++) {
            if (chinese[i].equals(str)) {
                return i;
            }
        }
        ToastUtil.toast("没找到这个汉字对应的数字");
        return 0;
    }


    public static int changeSimpleChineseCharToInteger(String str) {
        if (str.length() > 1) {
            ToastUtil.toast("长度过长");
            return 0;
        }
        for (int i = 0; i < chinese.length; i++) {
            if (simpleChinese[i].equals(str)) {
                return i;
            }
        }
        ToastUtil.toast("没找到这个汉字对应的数字");
        return 0;
    }

    public static String changeIntegerToChaneseChar(int num) {
        String numstr = num + "";
        String finnalStr = "";
        String unit[] = {"","拾", "佰", "仟", "萬"};
        int j=0;
        for (int i = numstr.length()-1; i >=0 ; i--) {
            finnalStr = chinese[Integer.parseInt(numstr.charAt(i)+"")]+unit[j%5]+finnalStr;
            j++;
        }
        return finnalStr.replace("萬萬","億").replace("拾零","拾").replace("壹拾","拾");
    }


    public static String changeIntegerToSimpleChaneseChar(int num) {
        String numstr = num + "";
        String finnalStr = "";
        String unit[] = {"","十", "百", "千", "万"};
        int j=0;
        for (int i = numstr.length()-1; i >=0 ; i--) {
            finnalStr = simpleChinese[Integer.parseInt(numstr.charAt(i)+"")]+unit[j%5]+finnalStr;
            j++;
        }
        return finnalStr.replace("万万","亿").replace("十零","十").replace("一十","十");
    }


}
