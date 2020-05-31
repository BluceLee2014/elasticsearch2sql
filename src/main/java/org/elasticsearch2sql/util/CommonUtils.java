package org.elasticsearch2sql.util;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * @author 306548063@qq.com
 * @Desc
 * @date 2020/5/31 22:45
 */
public class CommonUtils {

    public static final String BACKSLASH = "/";

    public static boolean isEmpty(@Nullable Object object){
        return ObjectUtils.isEmpty(object);
    }

    public static boolean isNotEmpty(@Nullable Object object){
        return !ObjectUtils.isEmpty(object);
    }

}
