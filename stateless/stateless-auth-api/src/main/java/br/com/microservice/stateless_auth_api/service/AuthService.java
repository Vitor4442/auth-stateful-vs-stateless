package br.com.microservice.stateless_auth_api.service;

import br.com.microservice.stateless_auth_api.dto.AuthRequest;
import br.com.microservice.stateless_auth_api.dto.TokenDTO;
import br.com.microservice.stateless_auth_api.infra.exception.ValidationException;
import br.com.microservice.stateless_auth_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class AuthService {
    //to-do crair um meio de criar usuario so com a role
    //to-do implementar roles
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenDTO login(AuthRequest request){
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ValidationException("User not found!"));
        validationPassword(request.password(), user.getPassword());
        var accessToken = jwtService.createToken(user);
        return new TokenDTO(accessToken);
    }

    private void validationPassword(String rawPassword, String encodedPassword){
        if(isEmpty(rawPassword)){
            throw new ValidationException("The password must be informed!");
        }
        if(!passwordEncoder.matches(rawPassword, encodedPassword)){
            throw new ValidationException("The password is incorrect!");
        }
    }

    public TokenDTO validateToken(String accessToken){
        validateExistingToken(accessToken);
        jwtService.validateAcessToken(accessToken);
        return new TokenDTO(accessToken);
    }

    private void validateExistingToken(String acessToken){
        if(isEmpty(acessToken)){
            throw new ValidationException("The access token must be informed1");
        }
    }
}
