package com.zone7.demo.helloworld.commons.exception;

import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;
import lombok.Data;

/**
 * DuplicationException
 * 数据重复异常
 *
 * @author: zone7
 * @time: 2019.02.15
 */
@Data
public class DuplicationException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_DATA_DUPLICATION;

    public DuplicationException(String message) {
        super(message);
    }
}
