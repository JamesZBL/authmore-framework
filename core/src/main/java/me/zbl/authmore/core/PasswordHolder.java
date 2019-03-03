package me.zbl.authmore.core;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-05
 */
public interface PasswordHolder {

    String getPassword();

    void setPassword(String encoded);
}
