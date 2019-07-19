package com.zone7.demo.helloworld.commons.exception;

import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;
import lombok.Data;

/**
 * DeleteOPException
 * 删除操作异常
 *
 * @author: zone7
 * @time: 2019.02.15
 */
@Data
public class DeleteOPException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_DELETE_OP;

    public DeleteOPException(String message) {
        super(message);
    }

}
