package br.com.microservice.stateless_auth_api.controller;

import br.com.microservice.stateless_auth_api.dto.AuthRequest;
import br.com.microservice.stateless_auth_api.dto.TokenDTO;
import br.com.microservice.stateless_auth_api.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public TokenDTO login(@RequestBody AuthRequest request){
        return authService.login(request);
    }

    @PostMapping("token/validate")
    public TokenDTO login(@RequestHeader String acessTokenn){
        return authService.validateToken(acessTokenn);
    }
}
