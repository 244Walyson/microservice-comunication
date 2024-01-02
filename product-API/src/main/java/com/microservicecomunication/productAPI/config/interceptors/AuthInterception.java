package com.microservicecomunication.productAPI.config.interceptors;

import com.microservicecomunication.productAPI.exception.ValidateException;
import com.microservicecomunication.productAPI.services.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

public class AuthInterception implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transactionid";
    private static final String SERVICE_ID = "serviceid";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(isOptions(request)){
            return true;
        }
        if(request.getHeader(TRANSACTION_ID).isEmpty()){
            throw new ValidateException("The transactionid header is required");
        }
        var authorization = request.getHeader(AUTHORIZATION);
        jwtService.validateAuthentication(authorization);
        request.setAttribute(SERVICE_ID, UUID.randomUUID().toString());
        return true;
    }

    private boolean isOptions(HttpServletRequest request){
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }

}
