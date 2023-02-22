package com.shanchui.aspect;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.shanchui.model.R;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobaExceptionHander {

    /**
     * 1、内部api调用的异常处理
     */

    @ExceptionHandler(value = ApiException.class)
    public R handlerApiException(ApiException exception) {
        IErrorCode errorCode = exception.getErrorCode();
        if(errorCode != null) {
            return R.fail(errorCode);
        }
        return R.fail(exception.getMessage());
    }

    /**
     * 2、方法校验失败的异常
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  R handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if(fieldError != null) {
                return R.fail(fieldError.getField()+fieldError.getDefaultMessage());
            }
        }
        return R.fail(exception.getMessage());
    }

    /**
     * 对象内部使用@Validated校验失败的异常
     */
    @ExceptionHandler(BindException.class)
    public R handlerBindException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if(fieldError != null) {
                return R.fail(fieldError.getField()+fieldError.getDefaultMessage());
            }
        }
        return R.fail(exception.getMessage());
    }
}
