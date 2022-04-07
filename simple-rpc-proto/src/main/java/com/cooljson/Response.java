package com.cooljson;

import lombok.Data;

/**
 * @author ChenJie
 * @date 2022/4/3
 * simple-rpc服务调用的响应体
 */

@Data
public class Response {
    // 响应码
    private int code = 200;

    // 返回的提示信息
    private String message = "ok";

    // 返回的数据
    private Object data;
}
