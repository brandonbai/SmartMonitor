package com.github.brandonbai.smartmonitor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 异常处理
 * @author Feihu Ji
 * @since 2017年10月15日
 *
 */

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
	private static final String MSG_500 = "服务运行异常";
	
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
        return Response.err(e.getMessage());
    }
}