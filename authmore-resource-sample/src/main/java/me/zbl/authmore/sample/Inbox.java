package me.zbl.authmore.sample;

import java.util.List;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-18
 */
public class Inbox {

    private List<Email> emails;

    public Inbox(List<Email> emails) {
        this.emails = emails;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public Inbox setEmails(List<Email> emails) {
        this.emails = emails;
        return this;
    }
}
