package com.hekf.base.exception;

/**  授权token空异常
 * @program: qyhx-sso
 * @description:
 * @author: hekf
 * @create: 2023-08-01 16:52
 **/
public class TokenNullException extends Exception {

    public TokenNullException(){}

    public TokenNullException(String message) {
        super(message);
    }

}
