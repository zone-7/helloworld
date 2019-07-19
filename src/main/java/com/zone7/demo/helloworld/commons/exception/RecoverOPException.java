package com.zone7.demo.helloworld.commons.exception;

import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;

/**
 * RecoverOPException
 *
 * @author: zone7
 * @time: 2019.02.19
 */
public class RecoverOPException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_RECOVER_OP;

    public RecoverOPException(String message) {
        super(message);
    }

}
