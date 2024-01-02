package com.microservicecomunication.productAPI.config.interceptors;

import com.microservicecomunication.productAPI.exception.ValidateException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import static com.microservicecomunication.productAPI.config.interceptors.RequestUtils.getCurrentRequest;

@Component
public class FeignClientAuthInterceptor implements RequestInterceptor {

    private final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTIONAL_ID = "transactionid";


    @Override
    public void apply(RequestTemplate requestTemplate) {
        var currentRequest = getCurrentRequest();
        requestTemplate
                .header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION))
                .header(TRANSACTIONAL_ID, currentRequest.getHeader(TRANSACTIONAL_ID));
    }

}
