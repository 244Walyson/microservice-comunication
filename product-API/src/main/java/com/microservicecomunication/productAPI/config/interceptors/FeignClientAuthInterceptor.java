package com.microservicecomunication.productAPI.config.interceptors;

import com.microservicecomunication.productAPI.exception.ValidateException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

public class FeignClientAuthInterceptor implements RequestInterceptor {

    private final String AUTHORIZATION = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        var currentRequest = getCurrentRequest();
        requestTemplate.header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION));
    }

    private HttpServletRequest getCurrentRequest(){
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
