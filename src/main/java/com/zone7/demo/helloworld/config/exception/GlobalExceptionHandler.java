package com.zone7.demo.helloworld.config.exception;

import com.zone7.demo.helloworld.commons.exception.DeleteOPException;
import com.zone7.demo.helloworld.commons.exception.DuplicationException;
import com.zone7.demo.helloworld.commons.exception.NoResultException;
import com.zone7.demo.helloworld.commons.exception.ParamException;
import com.zone7.demo.helloworld.commons.response.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 * 全局异常处理器
 * 当请求处理出现异常时，会根据异常处理器的配置顺序，依次尝试异常匹配和处理
 * 自定义异常处理器需要继承GlobalException类
 *
 * @author: zone7
 * @time: 2019.02.14
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO: 添加自定义的异常处理器

    @ExceptionHandler(value = DeleteOPException.class)
    @ResponseBody
    public ResponseData deleteExceptionHandler(HttpServletRequest request, DeleteOPException e) throws Exception {
        return ResponseData.error(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(value = NoResultException.class)
    @ResponseBody
    public ResponseData noResultExceptionHandler(HttpServletRequest request, NoResultException e) throws Exception {
        return ResponseData.error(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(value = DuplicationException.class)
    @ResponseBody
    public ResponseData duplicationExeptionHandler(HttpServletRequest request, DuplicationException e) throws Exception {
        return ResponseData.error(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(value = ParamException.class)
    @ResponseBody
    public ResponseData paramExeptionHandler(HttpServletRequest request, ParamException e) throws Exception {
        return ResponseData.error(e.getResponseCode().getCode(), e.getMessage());
    }

    /**
     * 全局异常处理器
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public ResponseData globalExceptionHandler(HttpServletRequest request, GlobalException e) throws Exception {
        return ResponseData.error(e.getResponseCode().getCode(), e.getMessage());
    }

    /**
     * 默认异常处理器
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        String errorMsg = e.getMessage();
        return ResponseData.errorMessage("[未知异常信息] " + errorMsg);
    }
}
