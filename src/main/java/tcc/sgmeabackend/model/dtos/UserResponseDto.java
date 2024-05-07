package tcc.sgmeabackend.model.dtos;

import tcc.sgmeabackend.model.enums.UserRole;

public record UserResponseDto(String nome,  String cpf, String email, UserRole role) {
}
