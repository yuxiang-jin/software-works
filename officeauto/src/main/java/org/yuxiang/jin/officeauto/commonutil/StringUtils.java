package org.yuxiang.jin.officeauto.commonutil;

import org.springframework.lang.Nullable;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */
public abstract class StringUtils {

    public StringUtils() {}

    public static boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }
}
