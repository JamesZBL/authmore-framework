package me.zbl.reactivesecurity.common.entity;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
public class ResponseContent {

    private String status;
    private String msg;

    public ResponseContent(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
