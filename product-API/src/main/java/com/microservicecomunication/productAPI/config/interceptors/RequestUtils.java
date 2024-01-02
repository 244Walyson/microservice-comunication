package com.microservicecomunication.productAPI.config.interceptors;

import com.microservicecomunication.productAPI.exception.ValidateException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {

    public static HttpServletRequest getCurrentRequest(){
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest();
        }catch (Exception e){
            e.printStackTrace();
            throw new ValidateException("The current request could not be processed");
        }
    }
}
