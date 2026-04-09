package br.com.microservice.stateless_any_api.service;

import br.com.microservice.stateless_any_api.dto.AnyResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnyService {

    private final JwtService jwtservice;

    public AnyResponse getData (String acessToken){
        jwtservice.ValidateAccessToken(acessToken);
        var authUser = jwtservice.getAuthenticatedUser(acessToken);
        var ok = HttpStatus.OK;

        return new AnyResponse(ok.name(), ok.value(), authUser);
    }
}
