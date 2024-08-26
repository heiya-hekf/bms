package com.hekf.base.common.response;


import org.springframework.stereotype.Component;

@Component
public class ResultGenerator {

    /**
     * 默认成功,无数据
     * @return
     */
    public ResponseData successResult() {
        return new ResponseData(ResultCode.SUCCESS);
    }
    /**
     * 默认成功,有数据
     * @param data
     * @return
     */
    public ResponseData successResult(Object data) {
        return new ResponseData(ResultCode.SUCCESS,data);
    }
    /**
     * 默认失败,无数据
     * @return
     */
    public ResponseData failResult() {
        return new ResponseData(ResultCode.FAIL);
    }
    /**
     * 默认失败
     * @param message 自定义失败信息
     * @return
     */
    public static ResponseData failResult(String message) {
        return new ResponseData(ResultCode.FAIL,message);
    }


    /**
     * 采用枚举中的状态无数据返回
     * @param resultCode 响应码枚举
     * @return
     */
    public static ResponseData freeResult(ResultCode resultCode) {
        return new ResponseData(resultCode);
    }

    /**
     * 采用枚举中的状态带数据返回
     * @param resultCode 响应码枚举
     * @param data
     * @return
     */
    public ResponseData freeResult(ResultCode resultCode, Object data) {
        return new ResponseData(resultCode, data);
    }


    /**
     * 自定义返回信息
     * @param code 响应码
     * @param message 自定义失败信息
     * @param data
     * @return
     */
    public ResponseData freeResult(String code, String message, Object data) {
        return new ResponseData(code, message, data);
    }

}
