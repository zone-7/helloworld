package com.zone7.demo.helloworld.commons.exception;

import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;
import lombok.Data;

/**
 * ParamException
 *
 * @author: zone7
 * @time: 2019.02.14
 */
@Data
public class ParamException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_PARAM_ILLGAL;

    public ParamException(String message) {
        super(message);
    }
}
