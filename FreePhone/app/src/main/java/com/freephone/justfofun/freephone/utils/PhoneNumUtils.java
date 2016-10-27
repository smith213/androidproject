package com.freephone.justfofun.freephone.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shenh on 2016/10/27.
 */

public class PhoneNumUtils {
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String clearRedundancy(String phoneNum){
        return phoneNum.replace(" ","").replace("-","").replace("+","");
    }
}
