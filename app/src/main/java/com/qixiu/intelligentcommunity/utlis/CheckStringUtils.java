package com.qixiu.intelligentcommunity.utlis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/15 0015.
 * 字符串校验类
 */

public class CheckStringUtils {

    public static boolean isMobileNO(String mobiles) {
//		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-1,5-9]))\\d{8}$");
//		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
        Pattern p = Pattern.compile("^[1][3,4,5,7,8]+\\d{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();

    }
}
