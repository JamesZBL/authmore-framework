package me.zbl.reactivesecurity.common;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-10
 */
public class RandomSecretTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomSecretTest.class);

    @BeforeEach
    void setup() {
        BasicConfigurator.configure();
    }

    @Test
    void build() {
        String last = null;
        HashSet<Object> digests = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String pwd = RandomSecret.create();
            char[] chars = pwd.toCharArray();
            assertNotNull(pwd);
            assertEquals(32, pwd.length());
            assertNotEquals(last, pwd);
            for (int j = 0; j < pwd.length(); j++) {
                char c = chars[j];
                assertTrue(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z');
            }
            assertFalse(digests.contains(pwd));
            digests.add(pwd);
            last = pwd;
            LOGGER.debug("New random password: {}", pwd);
        }
    }
}
