package com.kaiyu.model;

import lombok.Getter;

/**
 * @author mxxxl
 * @date 2021/6/15
 */
@Getter
public class ResponseMsg {

    private Integer code;
    private String msg;
    private Object data;

    private ResponseMsg(){}

    private ResponseMsg(Integer code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseMsg build() {
        return new ResponseMsg();
    }

    public static ResponseMsg ok(String msg){
        return new ResponseMsg(200,msg,null);
    }

    public static ResponseMsg ok(String msg,Object data){
        return new ResponseMsg(200,msg,data);
    }

    public static ResponseMsg error(String msg){
        return new ResponseMsg(500,msg,null);
    }

    public static ResponseMsg error(String msg,Object data){
        return new ResponseMsg(500,msg,data);
    }

    public ResponseMsg setCode(Integer code) {
        this.code = code;
        return this;
    }

    public ResponseMsg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResponseMsg setData(Object data) {
        this.data = data;
        return this;
    }
}
