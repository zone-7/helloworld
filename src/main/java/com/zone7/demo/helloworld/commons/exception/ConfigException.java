package com.zone7.demo.helloworld.commons.exception;

import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;
import lombok.Data;

/**
 * ParamException
 *
 */
@Data
public class ConfigException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_PARAM_ILLGAL;

    public ConfigException(String message) {
        super(message);
    }
}
