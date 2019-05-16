package me.zbl.authmore.sample;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-18
 */
public class Email {

    private String subject;
    private String from;
    private String to;
    private String content;

    public String getSubject() {
        return subject;
    }

    public Email setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Email setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public Email setTo(String to) {
        this.to = to;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Email setContent(String content) {
        this.content = content;
        return this;
    }
}
