package com.fengx.nettyboot.tcp.entity;

import lombok.Data;

/**
 * @author Fengx
 * @description：实体类
 **/
@Data
public class RpcResponse {

    private String id;
    private Object data;
    private int status;

    @Override
    public String toString() {
        return "RpcResponse{" + "id='" + id + '\'' + ", data=" + data + ", status=" + status + '}';
    }

}
