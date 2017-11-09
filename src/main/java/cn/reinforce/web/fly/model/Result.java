package cn.reinforce.web.fly.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Fate on 2016/11/23.
 */
public class Result {

    @Expose
    private boolean success;

    @Expose
    private int errorCode;

    @Expose
    private String msg;

    @Expose
    private String requestId = "";

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Result(boolean success, int errorCode, String msg) {
        this.success = success;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public Result(boolean success, int errorCode, String msg, String requestId) {
        this.success = success;
        this.errorCode = errorCode;
        this.msg = msg;
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }
}
