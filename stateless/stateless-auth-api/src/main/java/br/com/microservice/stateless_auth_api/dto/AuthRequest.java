package br.com.microservice.stateless_auth_api.dto;

public record AuthRequest(String username, String password) {
}
