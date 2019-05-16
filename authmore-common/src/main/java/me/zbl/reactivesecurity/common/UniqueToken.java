package me.zbl.reactivesecurity.common;

import org.springframework.util.Base64Utils;

import java.util.UUID;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-24
 */
public class UniqueToken {

    private UniqueToken() {}

    public static String create() {
        return Base64Utils.encodeToUrlSafeString(UUID.randomUUID().toString().getBytes());
    }
}
