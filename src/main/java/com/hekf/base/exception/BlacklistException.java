package com.hekf.base.exception;

/**  授权渠道黑名单异常处理
 * @BelongsProject qyhx-sso
 * @BelongsPackage com.seeyii.base.exception
 * @Author hekf
 * @Date 2023/7/27 14:32
 * @Version 1.0
 */
public class BlacklistException extends Exception {

    private static final long serialVersionUID = -60875313613216548L;

    public BlacklistException(){}

    public BlacklistException(String message) {
        super(message);
    }


}
