package tcc.sgmeabackend.model.dtos;

import tcc.sgmeabackend.model.enums.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
}
