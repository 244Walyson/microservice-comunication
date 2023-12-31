package com.microservicecomunication.productAPI.services.jwt;

import com.microservicecomunication.productAPI.dto.jwt.JwtDTO;
import com.microservicecomunication.productAPI.exception.AuthorizationException;
import com.microservicecomunication.productAPI.exception.ValidateException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${app-config.secrets.api-secret}")
    private String API_SECRET;
    private final String EMPTY_SPACE = " ";

    public void validateAuthentication(String token){
        var accessToken = extractToken(token);
        try {
            var claims = Jwts
                    .parser()
                    .setSigningKey(Keys.hmacShaKeyFor(API_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            JwtDTO user = JwtDTO.getUser(claims);
            logger.info(user.toString());
            if(claims.isEmpty() || user.getName() == null){
                throw new AuthorizationException("The user is not valid");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new AuthorizationException("Error while trying to process the Access Token");
        }
    }

    private String extractToken(String token){
        if(token == null || token.isEmpty()){
            throw new AuthorizationException("Token could not be empty");
        }else if(token.contains(EMPTY_SPACE)){
            return token.split(EMPTY_SPACE)[1];
        }
        return token;
    }

}
