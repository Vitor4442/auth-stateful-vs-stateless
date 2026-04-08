package br.com.microservice.stateless_any_api.service;

import br.com.microservice.stateless_any_api.dto.AuthUserResponse;
import br.com.microservice.stateless_any_api.exception.AuthenticationException;
import br.com.microservice.stateless_any_api.exception.ValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;

    @Value("${app.token.secret-key}")
    private String secretKey;

    public AuthUserResponse getAuthenticatedUser(String token) {
        var tokenClaims = getClaims(token);
        var userId = Integer.valueOf(tokenClaims.get("id").toString());
        return new AuthUserResponse(userId, (String) tokenClaims.get("username"));
    }

    private Claims getClaims(String token){
        var acessToken = extractToken(token);
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(generateSign())
                    .build()
                    .parseClaimsJws(acessToken)
                    .getBody();
        } catch (Exception ex){
            throw new AuthenticationException("Invalid token " + ex.getMessage());
        }
    }

    public void ValidateAccessToken(String token){
        getClaims(token);
    }

    private String extractToken(String token){
        if(isEmpty(token)){
            throw new ValidationException("The acesse token was not informed.");
        }

        if(token.contains(EMPTY_SPACE)){
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }

        return token;
    }

    private SecretKey generateSign(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
