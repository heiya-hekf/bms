package com.hekf.base.common.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseData implements Serializable {

    private static final long serialVersionUID = 13413321539131L;

    private String code;
    private String msg;
    private String time;
    @JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
    private Object data;

    /* 构造 */
    public ResponseData() {
        super();
    }

    public ResponseData(String code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.time = time;
        this.data = data;
    }

    /**
     * 默认成功或者失败,无数据
     * @param resultCode
     */
    public ResponseData(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.time = resultCode.getTime();
    }

    /**
     * 枚举结果以及获取数据
     * @param resultCode
     * @param msg
     */
    public ResponseData(ResultCode resultCode,String msg) {
        this.code = resultCode.getCode();
        this.msg = msg;
        this.time = resultCode.getTime();;
    }

    /**
     * 枚举结果以及获取数据
     * @param resultCode
     * @param data
     */
    public ResponseData(ResultCode resultCode,Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.time = resultCode.getTime();
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }



}
