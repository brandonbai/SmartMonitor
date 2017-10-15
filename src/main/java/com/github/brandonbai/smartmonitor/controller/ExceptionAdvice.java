package com.github.brandonbai.smartmonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.Response;

/**
 * 
 * ExceptionAdvice 
 * @Description: 异常处理
 * @author Feihu Ji
 * @sine 2017年10月15日
 *
 */

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

	private static final Logger logger = Logger.getLogger(ExceptionAdvice.class);
	private static final String MSG_400 = "请求参数解析失败";
	private static final String MSG_500 = "服务运行异常";
	
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error(MSG_400, e);
        return new Response().failure(MSG_400);
    }
    
    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        if(e instanceof MsgException) {
            logger.error(e.getMessage());
        }else {
            logger.error(MSG_500, e);
        }
        return new Response().failure(e.getMessage());
    }
}