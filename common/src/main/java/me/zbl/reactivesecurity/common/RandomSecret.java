package me.zbl.reactivesecurity.common;

import java.util.Random;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-10
 */
public class RandomSecret {

    private RandomSecret() {}

    public static String create() {
        return create(32);
    }

    public static String create(int length) {
        int p = 0;
        char[] table = new char[62];
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (char i = '0'; i <= '9'; i++) {
            table[p++] = i;
        }
        for (char i = 'A'; i <= 'Z'; i++) {
            table[p++] = i;
        }
        for (char i = 'a'; i <= 'z'; i++) {
            table[p++] = i;
        }
        for (int i = 0; i < length; i++) {
            int po = random.nextInt(62);
            sb.append(String.valueOf(table[po]));
        }
        return sb.toString();
    }
}
