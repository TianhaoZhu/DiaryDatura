package zth.com.gezhi.bean;

import java.io.Serializable;

/**
 * Created by Kotle on 2017/9/26.
 */

public class BaseBean implements Serializable{
    private String status;
    private String code;
    private String msg;
    private int optRzt;
    private int total;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOptRzt() {
        return optRzt;
    }

    public void setOptRzt(int optRzt) {
        this.optRzt = optRzt;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
