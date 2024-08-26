package com.hekf.base.common.response;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum ResultCode {

    /* 成功 */
    SUCCESS("200", "Success"),
    /* 失败 */
    FAIL("500", "Fail"),

    /* 参考HTTP状态码 */
    REQUEST_PARAMS_NOT_FOUND("201", "request Params Not Found"), //缺少请求入参
    USER_OR_PASSWORD_ERROR("301", "user or password error"), // 用户名或密码错误
    PASSWORD_IS_NOT_FOUND("302", "password is not found"), // 缺少密码
    USERNAME_IS_NOT_FOUND("303", "username is not found"), // 缺少用户名称
    CHANNEL_IS_NOT_FOUND("303", "channel is not found"), // 缺少渠道号码
    PHONE_IS_NOT_FOUND("304", "phone is not found"), // 缺少电话号码

    JWT_TOKEN_NOT_FOUND("700", "Token Not Found "),
    JWT_TOKEN_EXPIRED("701", "Token Expired "),
    JWT_SIGNATURE_VERIFICATION("702", "Signature Verification "),
    JWT_ALGORITHM_MISMATCH("703", "Algorithm Mismatch "),
    JWT_DECODE_EXCEPTION("704", "Decode Exception "),
    JWT_TOKEN_BLACKLIST_EXCEPTION("707", "Token Blacklist Exception "),
    ;


    private String code;
    private String message;
    private String time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public void setTime(String time) {
        this.time = time;
    }

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }



}
