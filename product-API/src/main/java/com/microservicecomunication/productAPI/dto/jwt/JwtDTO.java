package com.microservicecomunication.productAPI.dto.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {

    private int id;
    private String name;
    private String email;

    public static JwtDTO getUser(Claims claims){
        try {
            return new ObjectMapper().convertValue(claims.get("authUser"), JwtDTO.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
