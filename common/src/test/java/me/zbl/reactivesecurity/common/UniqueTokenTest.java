package me.zbl.reactivesecurity.common;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-24
 */
class UniqueTokenTest {

    @Test
    void create() {
        Set<Object> set = new HashSet<>();
        for (int i = 0; i < 10E6; i++) {
            String token = UniqueToken.create();
            assertNotNull(token);
            assertFalse(set.contains(token));
            set.add(token);
        }
    }
}