package com.fengx.nettyboot.tcp.entity;

import lombok.Data;

/**
 * @author Fengx
 * @description：实体类
 **/
@Data
public class RpcRequest {

    private String id;
    private Object data;

    @Override
    public String toString() {
        return "RpcRequest{" + "id='" + id + '\'' + ", data=" + data + '}';
    }

}
