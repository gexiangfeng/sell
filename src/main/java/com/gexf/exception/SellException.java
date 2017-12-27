package com.gexf.exception;

import com.gexf.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by Gexf on 2017/7/22.
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;
    private String message;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
