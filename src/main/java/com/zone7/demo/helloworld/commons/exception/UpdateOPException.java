package com.zone7.demo.helloworld.commons.exception;

import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;
import lombok.Data;

/**
 * UpdateOPException
 *
 * @author: zone7
 * @time: 2019.02.15
 */
@Data
public class UpdateOPException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_UPDATE_OP;

    public UpdateOPException(String message) {
        super(message);
    }

}
