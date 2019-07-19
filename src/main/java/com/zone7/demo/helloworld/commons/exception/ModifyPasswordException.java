package com.zone7.demo.helloworld.commons.exception;

import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;

/**
 * ModifyPasswordException
 *
 * @author: zone7
 * @time: 2019.02.22
 */
public class ModifyPasswordException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_MODIFY_PASSWORD;

    public ModifyPasswordException(String message) {
        super(message);
    }

}
