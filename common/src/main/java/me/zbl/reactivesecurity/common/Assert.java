package me.zbl.reactivesecurity.common;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-02
 */
public final class Assert {

    private Assert() {}

    public static void notEmpty(@Nullable String value, String message) {
        if (StringUtils.isEmpty(value))
            throw new IllegalArgumentException(message);
    }
}
