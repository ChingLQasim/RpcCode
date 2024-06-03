package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse implements Serializable {

    /**
     * 数据
     */
    private Object data;

    /**
     * 数据类型
     */
    private Class<?> dataType;

    /**
     * 相应消息
     */
    private String message;

    /**
     * 异常信息
     */
    private Exception exception;


}
