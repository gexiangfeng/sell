package com.gexf.utils;

import com.gexf.enums.CodeEnum;
import com.gexf.enums.OrderStatusEnum;

/**
 * Created by Gexf on 2017/8/2.
 */
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
