package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";


    //定义exception解决未被controller层吸收的exception 类似Spring的钩子方法

    //对于businssexception这种异常应该是业务逻辑处理上的问题或者是正常的业务逻辑错误，而并非是服务端不能处理的错误，导致返回internalservererror
    //因此我在这里定义说即使我们的controller抛出了exception，我们捕获他并返回一个statusok
    @ExceptionHandler(Exception.class) //需要指定收到什么样的exception才会处理，这里指定exception根类
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){

        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());

        }else{

            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());

        }

        return CommonReturnType.create(responseData, "fail");
    }
}
