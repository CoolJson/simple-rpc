package com.cooljson;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ChenJie
 * @date 2022/4/3
 * 网络传输的端点
 */

@Data
@AllArgsConstructor
public class Peer {
    // 主机地址
    private String host;

    // 端口号
    private int port;
}
