package me.zbl.authmore.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
public final class RequestUtil {

    private RequestUtil() {}

    public static String queryStringOf(Map<String, String> params) {
        List<String> stringParis = new ArrayList<>();
        params.forEach((k, v) -> stringParis.add(String.format("%s=%s", String.valueOf(k), String.valueOf(v))));
        return String.join("&", stringParis);
    }
}
