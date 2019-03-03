package me.zbl.reactivesecurity.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-11
 */
public class ResultBuilder {

    private Map<String, Object> map;

    public ResultBuilder() {
        this.map = new HashMap<>();
    }

    public ResultBuilder put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Map map() {
        return this.map;
    }
}
