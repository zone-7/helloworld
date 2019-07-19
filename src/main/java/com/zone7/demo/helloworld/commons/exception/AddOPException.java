package com.zone7.demo.helloworld.commons.exception;

import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;
import lombok.Data;

/**
 * AddOPException
 *
 * @author: zone7
 * @time: 2019.02.20
 */
@Data
public class AddOPException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_ADD_OP;

    public AddOPException(String message) {
        super(message);
    }

}
