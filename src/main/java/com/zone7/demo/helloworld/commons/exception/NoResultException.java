package com.zone7.demo.helloworld.commons.exception;
 
import com.zone7.demo.helloworld.commons.response.ResponseCode;
import com.zone7.demo.helloworld.config.exception.GlobalException;
import lombok.Data;

/**
 * NoResultException
 * 查询结果不存在异常
 *
 * @author: zone7
 * @time: 2019.02.15
 */
@Data
public class NoResultException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_GET_NO_RESULT;

    public NoResultException(String message) {
        super(message);
    }

}
